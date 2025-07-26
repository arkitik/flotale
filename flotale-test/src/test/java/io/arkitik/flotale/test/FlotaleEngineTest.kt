package io.arkitik.flotale.test

import io.arkitik.flotale.action.store.ActionStore
import io.arkitik.flotale.deploy.app.ArkitikFlotaleApp
import io.arkitik.flotale.element.flow.store.ElementFlowStore
import io.arkitik.flotale.element.store.ElementStore
import io.arkitik.flotale.engine.core.FlotaleDomainEngine
import io.arkitik.flotale.engine.core.FlotaleWorkflowEngine
import io.arkitik.flotale.engine.core.dto.ElementDetails
import io.arkitik.flotale.engine.core.dto.ReferenceData
import io.arkitik.flotale.engine.core.ext.persistWorkflow
import io.arkitik.flotale.engine.core.ext.task
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import io.arkitik.flotale.stage.store.StageStore
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import io.arkitik.flotale.task.store.TaskStore
import io.arkitik.flotale.test.mock.MockValidatorUnit
import io.arkitik.flotale.test.mock.MockValidatorUnits
import io.arkitik.flotale.workflow.store.WorkflowStore
import io.arkitik.radix.develop.shared.exception.NotAcceptableException
import io.arkitik.radix.develop.shared.exception.ResourceNotFoundException
import io.arkitik.radix.develop.store.delete
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:28 PM, 23 , **Fri, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
@SpringBootTest(
    classes = [
        ArkitikFlotaleApp::class,
        MockValidatorUnit::class
    ],

    )
@TestPropertySource(
    locations = ["classpath:application.yml"]
)
internal class FlotaleEngineTest {
    @Autowired
    private lateinit var flotaleWorkflowEngine: FlotaleWorkflowEngine

    @Autowired
    private lateinit var flotaleDomainEngine: FlotaleDomainEngine

    @Autowired
    private lateinit var mockValidatorUnit: MockValidatorUnit

    @Autowired
    private lateinit var actionStore: ActionStore

    @Autowired
    private lateinit var elementStore: ElementStore

    @Autowired
    private lateinit var elementFlowStore: ElementFlowStore

    @Autowired
    private lateinit var stageStore: StageStore

    @Autowired
    private lateinit var stageInitialStore: StageInitialStore

    @Autowired
    private lateinit var taskStore: TaskStore

    @Autowired
    private lateinit var taskInitialStore: TaskInitialStore

    @Autowired
    private lateinit var workflowStore: WorkflowStore


    private fun assertDetails(
        details: ElementDetails,
        expectedElementKey: String,
        expectedWorkflowKey: String,
        expectedStageKey: String,
        expectedTaskKey: String,
        expectedActions: List<String>,
    ) {
        assertEquals(expectedElementKey, details.elementKey)
        assertEquals(expectedWorkflowKey, details.workflow.key)
        assertEquals(expectedStageKey, details.stage.key)
        assertEquals(expectedTaskKey, details.task.key)
        assertTrue {
            (details.actions.isEmpty() && expectedActions.isEmpty()) ||
                    details.actions.map(ReferenceData::key).containsAll(
                        expectedActions
                    )
        }
    }

    private fun createJobWorkflow() {
        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "job-workflow"
                workflowName = "Job Execution workflow"
                initialStage {
                    stageKey = "pending-job-execution"
                    stageName = "Pending Stage"
                    initialTask {
                        taskKey = "pending-job-execution-task"
                        taskName = "Waiting"
                        addAction {
                            actionKey = "trigger-job"
                            actionName = "Run Job"
                            actionDestinationTask = "running-job-execution-task"
                        }
                        addAction {
                            actionKey = "cancel-waiting-job"
                            actionName = "Cancel pending job"
                            actionDestinationTask = "cancelled-job-execution-task"
                        }
                    }
                }
                addStage {
                    stageName = "Running Stage"
                    stageKey = "running-job-execution"
                    addStageTask {
                        taskKey = "running-job-execution-task"
                        taskName = "In Processing"
                        addAction {
                            actionKey = "mark-job-as-done"
                            actionName = "Mark As Done"
                            actionDestinationTask = "processed-job-execution-task"
                        }
                        addAction {
                            actionKey = "mark-job-as-failed"
                            actionName = "Mark As Failed"
                            actionDestinationTask = "failed-job-execution-task"
                        }
                        addAction {
                            actionKey = "internal-failure"
                            actionName = "Internal Failure"
                            actionDestinationTask = "internal-failure-job-execution-task"
                        }
                    }
                }
                addStage {
                    stageKey = "processed-job-execution"
                    stageName = "Processed Stage"
                    addStageTask {
                        taskKey = "processed-job-execution-task"
                        taskName = "Done"
                        terminal = true
                    }
                }
                addStage {
                    stageKey = "failed-job-execution"
                    stageName = "Failed Stage"
                    addStageTask {
                        taskKey = "failed-job-execution-task"
                        taskName = "Execution-Failed"
                        addAction {
                            actionKey = "re-trigger"
                            actionName = "Re-Trigger"
                            actionDestinationTask = "pending-job-execution-task"
                        }
                        addAction {
                            actionKey = "cancel-failed-job"
                            actionName = "Cancel failed job"
                            actionDestinationTask = "cancelled-job-execution-task"
                        }
                    }
                }
                addStage {
                    stageKey = "cancelled-job-execution"
                    stageName = "Cancelled Stage"
                    addStageTask {
                        taskKey = "cancelled-job-execution-task"
                        taskName = "Cancelled"
                        terminal = true
                    }
                }
                addStage {
                    stageKey = "internal-failed-job-execution"
                    stageName = "Internal-Failure"
                    addStageTask {
                        taskKey = "internal-failure-job-execution-task"
                        taskName = "Internal-Failure"
                        addAction {
                            actionKey = "internal-failed-job-execution-start-recovering"
                            actionName = "internal-failed-job-execution-start-recovering"
                            actionDestinationTask = "internal-failed-job-execution-recovering-task"
                        }
                    }
                }
                addStage {
                    stageKey = "internal-failed-job-execution-recovering"
                    stageName = "Internal Failed Recovering"
                    addStageTask {
                        taskKey = "internal-failed-job-execution-recovering-task"
                        taskName = "Recovering"
                        addAction {
                            actionKey = "internal-failed-job-execution-recovered"
                            actionName = "Recovered"
                            actionDestinationTask = "pending-job-execution-task"
                        }
                        addAction {
                            actionKey = "internal-failed-job-execution-failed"
                            actionName = "Failed internal-failed recovering job"
                            actionDestinationTask = "failed-job-execution-task"
                        }
                        addAction {
                            actionKey = "internal-failed-job-execution-cancel"
                            actionName = "Cancel internal-failed recovering job"
                            actionDestinationTask = "cancelled-job-execution-task"
                        }
                    }
                }
            }
        }
    }

    @BeforeEach
    fun setUp() {
        mockValidatorUnit.clearAll()
        elementFlowStore.delete(elementFlowStore.storeQuery.all())
        elementStore.delete(elementStore.storeQuery.all())
        actionStore.delete(actionStore.storeQuery.all())
        taskInitialStore.delete(taskInitialStore.storeQuery.all())
        taskStore.delete(taskStore.storeQuery.all())
        stageInitialStore.delete(stageInitialStore.storeQuery.all())
        stageStore.delete(stageStore.storeQuery.all())
        workflowStore.delete(workflowStore.storeQuery.all())
    }

    @Test
    fun givenUnregisteredElementKeyWhenFindDetailThenThrowsResourceNotFound() {
        assertThrows<ResourceNotFoundException> {
            flotaleWorkflowEngine.elementDetails("sample", "")
        }
    }

    @Test
    fun verifyWorkflowWithElementCreation() {
        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "WF"
                workflowName = "WF"
                initialStage {
                    stageKey = "WF-STAGE"
                    stageName = "WF-STAGE"
                    initialTask = task {
                        taskKey = "WF-TASK"
                        taskName = "WF-TASK"
                        terminal = true
                    }
                }
            }
        }
        val elementDetails = flotaleWorkflowEngine.initiateElement("WF", "ELEMENT-0", "TEST")
        assertEquals("WF", elementDetails.workflow.key)
        assertEquals("WF", elementDetails.workflow.name)
        assertEquals("WF-STAGE", elementDetails.stage.key)
        assertEquals("WF-STAGE", elementDetails.stage.name)
        assertEquals("WF-TASK", elementDetails.task.key)
        assertEquals("WF-TASK", elementDetails.task.name)
        assertEquals(0, elementDetails.actions.size)
    }

    @Test
    fun verifyWorkflowWithTerminalTaskCreation() {
        assertThrows<NotAcceptableException> {
            flotaleDomainEngine.persistWorkflow {
                addWorkflow {
                    workflowKey = "WF"
                    workflowName = "WF"
                    initialStage {
                        stageKey = "WF-STAGE"
                        stageName = "WF-STAGE"
                        initialTask {
                            taskKey = "WF-TASK"
                            taskName = "WF-TASK"
                            terminal = true
                            addAction {
                                actionKey = "WF-ACTION"
                                actionName = "WF-ACTION"
                                actionDestinationTask = "WF-TASK"
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    fun verifyWorkflowWithElementCreationAndActionExecutionWhenValidatorRespondActionCantBeExecuted() {

        mockValidatorUnit.registerVerifier(MockValidatorUnits.SupportedAndCantExecute)

        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "ABC"
                workflowName = "ABC"
                initialStage {
                    stageKey = "ABC-STAGE"
                    stageName = "ABC-STAGE"
                    initialTask {
                        taskKey = "ABC-TASK"
                        taskName = "ABC-TASK"
                        addAction {
                            actionKey = "ABC-ACTION"
                            actionName = "ABC-ACTION"
                            actionDestinationTask = "2ND-TASK"
                        }
                    }
                    addStageTask {
                        taskKey = "2ND-TASK"
                        taskName = "2ND-TASK"
                        terminal = true
                    }
                }
            }
        }
        flotaleWorkflowEngine.initiateElement("ABC", "ELEMENT-0", "TEST")


        val elementDetails = flotaleWorkflowEngine.elementDetails("ELEMENT-0", "TEST")

        assertEquals("ABC", elementDetails.workflow.key)
        assertEquals("ABC", elementDetails.workflow.name)
        assertEquals("ABC-STAGE", elementDetails.stage.key)
        assertEquals("ABC-STAGE", elementDetails.stage.name)
        assertEquals("ABC-TASK", elementDetails.task.key)
        assertEquals("ABC-TASK", elementDetails.task.name)
        assertEquals(0, elementDetails.actions.size)
        assertThrows<ResourceNotFoundException> {
            flotaleWorkflowEngine.executeAction("UNKNOWN_ACTION", "ELEMENT-0", "TEST")
        }
    }

    @Test
    fun `job-execution processed job workflow`() {

        createJobWorkflow()

        val job = flotaleWorkflowEngine.initiateElement(
            "job-workflow",
            "job-0",
            "TEST"
        )

        assertDetails(
            details = job,
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "pending-job-execution",
            expectedTaskKey = "pending-job-execution-task",
            expectedActions = listOf(
                "trigger-job",
                "cancel-waiting-job",
            )
        )

        flotaleWorkflowEngine.executeAction("trigger-job", job.elementKey, "TEST")

        assertDetails(
            details = flotaleWorkflowEngine.elementDetails(job.elementKey, "TEST"),
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "running-job-execution",
            expectedTaskKey = "running-job-execution-task",
            expectedActions = listOf(
                "mark-job-as-done",
                "mark-job-as-failed",
                "internal-failure",
            )
        )

        flotaleWorkflowEngine.executeAction("mark-job-as-done", job.elementKey, "TEST")

        assertDetails(
            details = flotaleWorkflowEngine.elementDetails(job.elementKey, "TEST"),
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "processed-job-execution",
            expectedTaskKey = "processed-job-execution-task",
            expectedActions = listOf()
        )
    }

    @Test
    fun `job-execution cancel job workflow`() {

        createJobWorkflow()

        val job = flotaleWorkflowEngine.initiateElement(
            "job-workflow",
            "job-0",
            "TEST"
        )
        assertDetails(
            details = job,
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "pending-job-execution",
            expectedTaskKey = "pending-job-execution-task",
            expectedActions = listOf(
                "trigger-job",
                "cancel-waiting-job",
            )
        )

        flotaleWorkflowEngine.executeAction("cancel-waiting-job", job.elementKey, "TEST")


        assertDetails(
            details = flotaleWorkflowEngine.elementDetails(job.elementKey, "TEST"),
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "cancelled-job-execution",
            expectedTaskKey = "cancelled-job-execution-task",
            expectedActions = listOf()
        )
    }

    @Test
    fun `job-execution internal-failed job workflow, recovering, execution-cancel`() {

        createJobWorkflow()

        val job = flotaleWorkflowEngine.initiateElement(
            "job-workflow",
            "job-0",
            "TEST"
        )
        assertDetails(
            details = job,
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "pending-job-execution",
            expectedTaskKey = "pending-job-execution-task",
            expectedActions = listOf(
                "trigger-job",
                "cancel-waiting-job",
            )
        )

        flotaleWorkflowEngine.executeAction("trigger-job", job.elementKey, "TEST")


        assertDetails(
            details = flotaleWorkflowEngine.elementDetails(job.elementKey, "TEST"),
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "running-job-execution",
            expectedTaskKey = "running-job-execution-task",
            expectedActions = listOf(
                "mark-job-as-done",
                "mark-job-as-failed",
                "internal-failure",
            )
        )

        flotaleWorkflowEngine.executeAction("internal-failure", job.elementKey, "TEST")
        assertDetails(
            details = flotaleWorkflowEngine.elementDetails(job.elementKey, "TEST"),
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "internal-failed-job-execution",
            expectedTaskKey = "internal-failure-job-execution-task",
            expectedActions = listOf(
                "internal-failed-job-execution-start-recovering",
            )
        )

        flotaleWorkflowEngine.executeAction("internal-failed-job-execution-start-recovering", job.elementKey, "TEST")
        assertDetails(
            details = flotaleWorkflowEngine.elementDetails(job.elementKey, "TEST"),
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "internal-failed-job-execution-recovering",
            expectedTaskKey = "internal-failed-job-execution-recovering-task",
            expectedActions = listOf(
                "internal-failed-job-execution-recovered",
                "internal-failed-job-execution-failed",
                "internal-failed-job-execution-cancel",
            )
        )

        flotaleWorkflowEngine.executeAction("internal-failed-job-execution-cancel", job.elementKey, "TEST")
        assertDetails(
            details = flotaleWorkflowEngine.elementDetails(job.elementKey, "TEST"),
            expectedElementKey = "job-0",
            expectedWorkflowKey = "job-workflow",
            expectedStageKey = "cancelled-job-execution",
            expectedTaskKey = "cancelled-job-execution-task",
            expectedActions = listOf()
        )
    }

}
