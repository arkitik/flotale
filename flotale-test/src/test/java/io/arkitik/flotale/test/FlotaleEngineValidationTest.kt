package io.arkitik.flotale.test

import io.arkitik.flotale.action.entity.exposed.FlotaleActionTable
import io.arkitik.flotale.action.store.ActionStore
import io.arkitik.flotale.deploy.app.ArkitikFlotaleApp
import io.arkitik.flotale.element.entity.exposed.FlotaleElementTable
import io.arkitik.flotale.element.flow.entity.exposed.FlotaleElementFlowTable
import io.arkitik.flotale.element.flow.store.ElementFlowStore
import io.arkitik.flotale.element.store.ElementStore
import io.arkitik.flotale.engine.core.FlotaleDomainEngine
import io.arkitik.flotale.engine.core.dto.WorkflowValidationResult
import io.arkitik.flotale.engine.core.ext.persistWorkflow
import io.arkitik.flotale.stage.entity.exposed.FlotaleStageTable
import io.arkitik.flotale.stage.initial.entity.exposed.FlotaleStageInitialTable
import io.arkitik.flotale.stage.initial.store.StageInitialStore
import io.arkitik.flotale.stage.store.StageStore
import io.arkitik.flotale.task.entity.exposed.FlotaleTaskTable
import io.arkitik.flotale.task.initial.entity.exposed.FlotaleTaskInitialTable
import io.arkitik.flotale.task.initial.store.TaskInitialStore
import io.arkitik.flotale.task.store.TaskStore
import io.arkitik.flotale.test.mock.MockValidatorUnit
import io.arkitik.flotale.workflow.entity.exposed.FlotaleWorkflowTable
import io.arkitik.flotale.workflow.store.WorkflowStore
import io.arkitik.radix.develop.exposed.table.ensureInTransaction
import io.arkitik.radix.develop.store.delete
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

/**
 * Created By Ibrahim Al-Tamimi 
 * Created At 4:30 PM, 26/07/2025
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
internal class FlotaleEngineValidationTest {

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
        ensureInTransaction {
            SchemaUtils.createMissingTablesAndColumns(
                FlotaleActionTable,
                FlotaleElementFlowTable,
                FlotaleElementTable,
                FlotaleStageInitialTable,
                FlotaleStageTable,
                FlotaleTaskInitialTable,
                FlotaleTaskTable,
                FlotaleWorkflowTable,
            )
        }
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

    // Test case 1: Non-existent workflow
    @Test
    fun `validate non-existent workflow`() {
        val result = flotaleDomainEngine.validateWorkflow("NON_EXISTENT_WORKFLOW")
        assertInstanceOf<WorkflowValidationResult.Companion.Invalid>(result)
        assertEquals(1, result.errors.size)
        assertEquals("NON_EXISTENT_WORKFLOW", result.errors[0].key)
        assertTrue(result.errors[0].reason.contains("does not exist"))
    }

    // Test case 2: Workflow without stages
    @Test
    fun `validate workflow without stages`() {
        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "EMPTY_WF"
                workflowName = "Empty Workflow"
            }
        }

        val result = flotaleDomainEngine.validateWorkflow("EMPTY_WF")
        assertInstanceOf<WorkflowValidationResult.Companion.Invalid>(result)
        assertEquals(1, result.errors.size)
        assertEquals("EMPTY_WF", result.errors[0].key)
        assertTrue(result.errors[0].reason.contains("does not have any stages"))
    }

    // Test case 3: Workflow without initial stage
    @Test
    fun `workflow without initial stage`() {
        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "WF"
                workflowName = "WF"
                addStage {
                    stageKey = "WF-STAGE"
                    stageName = "WF-STAGE"
                    initialTask {
                        taskKey = "WF-TASK"
                        taskName = "WF-TASK"
                        terminal = true
                    }
                }
            }
        }
        val result = flotaleDomainEngine.validateWorkflow("WF")
        assertInstanceOf<WorkflowValidationResult.Companion.Invalid>(result)
        assertTrue(result.errors.any { it.reason.contains("does not have an initial stage") })
    }

    // Test case 4: Initial stage without initial task
    @Test
    fun `workflow with initial stage but without initial task`() {
        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "WF"
                workflowName = "WF"
                initialStage {
                    stageKey = "WF-STAGE"
                    stageName = "WF-STAGE"
                    addTask {
                        taskKey = "WF-TASK"
                        taskName = "WF-TASK"
                        terminal = true
                    }
                }
            }
        }
        val result = flotaleDomainEngine.validateWorkflow("WF")
        assertInstanceOf<WorkflowValidationResult.Companion.Invalid>(result)
        assertTrue(result.errors.any { it.reason.contains("initial stage does not have an initial task") })
    }

    // Test case 5: Tasks without outgoing actions and not marked as terminal
    @Test
    fun `workflow with initial stage and initial task but without terminal task`() {
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
                    }
                }
            }
        }
        val result = flotaleDomainEngine.validateWorkflow("WF")
        assertInstanceOf<WorkflowValidationResult.Companion.Invalid>(result)
        assertTrue(result.errors.any { it.reason.contains("not marked as terminal tasks") })
    }

    // Test case 6: Stage without tasks
    @Test
    fun `workflow with stage that has no tasks`() {
        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "WF"
                workflowName = "WF"
                initialStage {
                    stageKey = "WF-STAGE-1"
                    stageName = "WF-STAGE-1"
                    initialTask {
                        taskKey = "WF-TASK-1"
                        taskName = "WF-TASK-1"
                        terminal = true
                    }
                }
                addStage {
                    stageKey = "WF-STAGE-2"
                    stageName = "WF-STAGE-2"
                    // No tasks added to this stage
                }
            }
        }
        val result = flotaleDomainEngine.validateWorkflow("WF")
        assertInstanceOf<WorkflowValidationResult.Companion.Invalid>(result)
        assertTrue(result.errors.any { it.reason.contains("stages do not have any tasks") })
        assertTrue(result.errors.any { it.reason.contains("WF-STAGE-2") })
    }

    // Test case 7: Unreachable tasks
    @Test
    fun `workflow with unreachable tasks`() {
        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "WF"
                workflowName = "WF"
                initialStage {
                    stageKey = "WF-STAGE"
                    stageName = "WF-STAGE"
                    initialTask {
                        taskKey = "WF-TASK-1"
                        taskName = "WF-TASK-1"
                        terminal = true
                    }
                    addTask {
                        taskKey = "WF-TASK-2"
                        taskName = "WF-TASK-2"
                        terminal = true
                    }
                }
            }
        }
        val result = flotaleDomainEngine.validateWorkflow("WF")
        assertInstanceOf<WorkflowValidationResult.Companion.Invalid>(result)
        assertTrue(result.errors.any { it.reason.contains("unreachable") })
        assertTrue(result.errors.any { it.reason.contains("WF-TASK-2") })
    }

    // Test case 9: Valid workflow configuration
    @Test
    fun `valid workflow configuration`() {
        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "VALID_WF"
                workflowName = "Valid Workflow"
                initialStage {
                    stageKey = "STAGE-1"
                    stageName = "Stage 1"
                    initialTask {
                        taskKey = "TASK-1"
                        taskName = "Task 1"
                        terminal = false
                        addAction {
                            actionKey = "ACTION-1"
                            actionName = "Action 1"
                            actionDestinationTask = "TASK-2"
                        }
                    }
                    addTask {
                        taskKey = "TASK-2"
                        taskName = "Task 2"
                        terminal = true
                    }
                }
            }
        }
        val result = flotaleDomainEngine.validateWorkflow("VALID_WF")
        assertInstanceOf<WorkflowValidationResult.Companion.Valid>(result)
    }

    // Test case 10: Multiple error conditions in one workflow
    @Test
    fun `workflow with multiple validation errors`() {
        flotaleDomainEngine.persistWorkflow {
            addWorkflow {
                workflowKey = "MULTI_ERROR_WF"
                workflowName = "Multi-Error Workflow"
                initialStage {
                    stageKey = "STAGE-1"
                    stageName = "Stage 1"
                    // Missing initial task
                    addTask {
                        taskKey = "TASK-1"
                        taskName = "Task 1"
                        terminal = false // Not terminal and no outgoing actions
                    }
                }
                addStage {
                    stageKey = "STAGE-2"
                    stageName = "Stage 2"
                    // No tasks at all
                }
            }
        }
        val result = flotaleDomainEngine.validateWorkflow("MULTI_ERROR_WF")
        assertInstanceOf<WorkflowValidationResult.Companion.Invalid>(result)
        assertTrue(result.errors.size >= 3) // At least 3 errors
        assertTrue(result.errors.any { it.reason.contains("initial task") })
        assertTrue(result.errors.any { it.reason.contains("do not have any tasks") })
        assertTrue(result.errors.any { it.reason.contains("not marked as terminal tasks") })
    }
}