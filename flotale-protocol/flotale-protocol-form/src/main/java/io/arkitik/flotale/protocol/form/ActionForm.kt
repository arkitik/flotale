package io.arkitik.flotale.protocol.form

/**
 * @author Ibrahim Al-Tamimi 
 * @since 00:07, Tuesday, 05/05/2026
 **/
data class ActionForm(
    val fields: List<ActionFormField>,
    val specs: Map<String, Any> = mapOf(),
)

data class ActionFormField(
    val fieldKey: String,
    val fieldLabel: String,
    val fieldType: String,
    val fieldOrder: Int,
    val fieldRequired: Boolean,
    val fieldReadOnly: Boolean,
    val fieldDescription: String? = null,
    val fieldOptions: List<ActionFormFieldOption>? = null,
    val fieldDefaultValue: Any? = null,
    val fieldHidden: Boolean = false,
    val fieldConfigs: Map<String, Any> = mapOf(),
)

data class ActionFormFieldOption(
    val key: String,
    val value: String,
)

@DslMarker
annotation class FlotaleFormBuilder

fun actionFormBuilder(builder: ActionFormBuilder.() -> Unit): ActionForm {
    return ActionFormBuilder().apply(builder).build()
}

@FlotaleFormBuilder
class ActionFormBuilder {
    private val fields = arrayListOf<ActionFormField>()
    private val specs = hashMapOf<String, Any>()


    fun addSpecs(fieldConfig: Pair<String, Any>) {
        addSpecs(fieldConfig.first, fieldConfig.second)
    }

    fun addSpecs(key: String, value: Any) {
        specs[key] = value
    }

    fun addSpecs(fieldSpecs: Map<String, Any>) {
        this.specs.putAll(fieldSpecs)
    }

    fun addField(fieldSpecs: ActionFormFieldBuilder.() -> Unit) {
        fields.add(ActionFormFieldBuilder().apply(fieldSpecs).build())
    }

    fun addField(field: ActionFormField) {
        fields.add(field)
    }

    fun build() = ActionForm(
        fields = fields,
        specs = specs
    )
}

@FlotaleFormBuilder
class ActionFormFieldBuilder {
    lateinit var fieldKey: String
    lateinit var fieldLabel: String
    lateinit var fieldType: String
    var fieldOrder: Int = 1
    var fieldRequired: Boolean = false
    var fieldReadOnly: Boolean = false
    var fieldDescription: String? = null
    private val fieldOptions = arrayListOf<ActionFormFieldOption>()
    var fieldDefaultValue: Any? = null
    var fieldHidden: Boolean = false
    private val fieldConfigs = hashMapOf<String, Any>()


    fun addConfigs(fieldConfig: Pair<String, Any>) {
        addConfig(fieldConfig.first, fieldConfig.second)
    }

    fun addConfig(key: String, value: Any) {
        fieldConfigs[key] = value
    }

    fun addConfigs(fieldConfigs: Map<String, Any>) {
        this.fieldConfigs.putAll(fieldConfigs)
    }

    fun addOption(key: String, value: String) {
        addOption(ActionFormFieldOption(key, value))
    }

    fun addOption(fieldOption: ActionFormFieldOption) {
        fieldOptions.add(fieldOption)
    }

    fun addOptions(fieldOptions: List<ActionFormFieldOption>) {
        this.fieldOptions.addAll(fieldOptions)
    }

    fun build(): ActionFormField =
        ActionFormField(
            fieldKey = fieldKey,
            fieldLabel = fieldLabel,
            fieldType = fieldType,
            fieldOrder = fieldOrder,
            fieldRequired = fieldRequired,
            fieldReadOnly = fieldReadOnly,
            fieldDescription = fieldDescription,
            fieldOptions = fieldOptions.takeIf { it.isNotEmpty() },
            fieldDefaultValue = fieldDefaultValue,
            fieldHidden = fieldHidden,
            fieldConfigs = fieldConfigs,
        )
}