package io.arkitik.flotale.test

import io.arkitik.flotale.action.store.ActionStore
import io.arkitik.flotale.deploy.app.ArkitikFlotaleApp
import io.arkitik.flotale.element.flow.store.ElementFlowStore
import io.arkitik.flotale.element.store.ElementStore
import io.arkitik.flotale.engine.core.FlotaleDomainEngine
import io.arkitik.flotale.engine.core.FlotaleWorkflowEngine
import io.arkitik.flotale.engine.core.ext.persistWorkflow
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import io.arkitik.flotale.stage.store.StageStore
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import io.arkitik.flotale.task.store.TaskStore
import io.arkitik.flotale.test.mock.MockValidatorUnit
import io.arkitik.flotale.test.mock.MockValidatorUnits
import io.arkitik.flotale.workflow.store.WorkflowStore
import io.arkitik.radix.develop.shared.exception.ResourceNotFoundException
import io.arkitik.radix.develop.store.delete
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import kotlin.test.assertEquals

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 7:28 PM, 23 , **Fri, December 2022**
 * Project *flotale* [arkitik.io](https://arkitik.io)
 */
@SpringBootTest(
    classes = [
        ArkitikFlotaleApp::class,
        MockValidatorUnit::class
    ]
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
            workflow {
                key("WF")
                name("WF")
                initialStage {
                    stageKey("WF-STAGE")
                    stageName("WF-STAGE")
                    stageInitialTask {
                        taskKey("WF-TASK")
                        taskName("WF-TASK")
                    }
                }
            }
        }
        flotaleWorkflowEngine.initiateElement("WF", "ELEMENT-0", "TEST")
        val elementDetails = flotaleWorkflowEngine.elementDetails("ELEMENT-0", "")
        assertEquals("WF", elementDetails.workflow.key)
        assertEquals("WF", elementDetails.workflow.name)
        assertEquals("WF-STAGE", elementDetails.stage.key)
        assertEquals("WF-STAGE", elementDetails.stage.name)
        assertEquals("WF-TASK", elementDetails.task.key)
        assertEquals("WF-TASK", elementDetails.task.name)
        assertEquals(0, elementDetails.actions.size)

        assertThrows<ResourceNotFoundException> {
            flotaleWorkflowEngine.executeAction("UNKNOWN_ACTION", "ELEMENT-0", "TEST")
        }
    }

    @Test
    fun verifyWorkflowWithElementCreationAndActionExecutionWhenValidatorRespondActionCantBeExecuted() {

        mockValidatorUnit.registerVerifier(MockValidatorUnits.SupportedAndCantExecute)

        flotaleDomainEngine.persistWorkflow {
            workflow {
                key("ABC")
                name("ABC")
                initialStage {
                    stageKey("ABC-STAGE")
                    stageName("ABC-STAGE")
                    stageInitialTask {
                        taskKey("ABC-TASK")
                        taskName("ABC-TASK")
                        taskAction {
                            actionKey("ABC-ACTION")
                            actionName("ABC-ACTION")
                            actionDestinationTask("2ND-TASK")
                        }
                    }
                    stageTask {
                        taskKey("2ND-TASK")
                        taskName("2ND-TASK")
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
    fun `job-execution workflow`() {

        flotaleDomainEngine.persistWorkflow {
            workflow {
                key("job-workflow")
                name("Job Execution workflow")
                initialStage {
                    stageKey("pending-job-execution")
                    stageName("Pending Stage")
                    stageInitialTask {
                        taskKey("pending-task-execution-task")
                        taskName("Waiting")
                        taskAction {
                            actionKey("trigger-job")
                            actionName("Run Job")
                            actionDestinationTask("running-task-execution-task")
                        }
                        taskAction {
                            actionKey("cancel-waiting-job")
                            actionName("Cancel pending job")
                            actionDestinationTask("cancelled-task-execution-task")
                        }
                    }
                }
                stage {
                    stageName("Running Stage")
                    stageKey("running-job-execution")
                    stageTask {
                        taskKey("running-task-execution-task")
                        taskName("In Processing")
                        taskAction {
                            actionKey("mark-job-as-done")
                            actionName("Mark As Done")
                            actionDestinationTask("processed-task-execution-task")
                        }
                        taskAction {
                            actionKey("mark-job-as-failed")
                            actionName("Mark As Failed")
                            actionDestinationTask("failed-task-execution-task")
                        }
                        taskAction {
                            actionKey("internal-failure")
                            actionName("Internal Failure")
                            actionDestinationTask("internal-failure-task-execution-task")
                        }
                    }
                }
                stage {
                    stageKey("processed-job-execution")
                    stageName("Processed Stage")
                    stageTask {
                        taskKey("processed-task-execution-task")
                        taskName("Done")
                    }
                }
                stage {
                    stageKey("failed-job-execution")
                    stageName("Failed Stage")
                    stageTask {
                        taskKey("failed-task-execution-task")
                        taskName("Execution-Failed")
                        taskAction {
                            actionKey("re-trigger")
                            actionName("Re-Trigger")
                            actionDestinationTask("pending-task-execution-task")
                        }
                        taskAction {
                            actionKey("cancel-failed-job")
                            actionName("Cancel failed job")
                            actionDestinationTask("cancelled-task-execution-task")
                        }
                    }
                }
                stage {
                    stageKey("cancelled-job-execution")
                    stageName("Cancelled Stage")
                    stageTask {
                        taskKey("cancelled-task-execution-task")
                        taskName("Cancelled")
                    }
                }
                stage {
                    stageKey("internal-failed-job-execution")
                    stageName("Internal-Failure")
                    stageTask {
                        taskKey("internal-failure-task-execution-task")
                        taskName("Internal-Failure")
                        taskAction {
                            actionKey("internal-failed-job-execution-start-recovering")
                            actionName("internal-failed-job-execution-start-recovering")
                            actionDestinationTask("internal-failed-job-execution-recovering-task")
                        }
                    }
                }
                stage {
                    stageKey("internal-failed-job-execution-recovering")
                    stageName("Internal Failed")
                    stageTask {
                        taskKey("internal-failed-job-execution-recovering-task")
                        taskName("Recovering")
                        taskAction {
                            actionKey("internal-failed-job-execution-recovered")
                            actionName("Recovered")
                            actionDestinationTask("pending-task-execution-task")
                        }
                        taskAction {
                            actionKey("internal-failed-job-execution-failed")
                            actionName("Failed internal-failed recovering job")
                            actionDestinationTask("failed-task-execution-task")
                        }
                        taskAction {
                            actionKey("internal-failed-job-execution-cancel")
                            actionName("Cancel internal-failed recovering job")
                            actionDestinationTask("cancelled-task-execution-task")
                        }
                    }
                }
            }
        }
        flotaleWorkflowEngine.initiateElement("job-workflow", "job-0", "TEST")
        flotaleWorkflowEngine.elementDetails("job-0", "TEST")
    }
}
