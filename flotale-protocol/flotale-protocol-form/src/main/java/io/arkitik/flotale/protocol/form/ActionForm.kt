package io.arkitik.flotale.protocol.form

/**
 * @author Ibrahim Al-Tamimi 
 * @since 00:07, Tuesday, 05/05/2026
 **/
data class ActionForm(
    val fields: List<ActionFormField>,
)

data class ActionFormField(
    val fieldLabel: String,
    val fieldType: String,
    val fieldOrder: Int,
    val fieldRequired: Boolean,
    val fieldReadOnly: Boolean,
    val fieldOptions: List<ActionFormFieldOption>? = null,
    val fieldDefaultValue: Any? = null,
    val fieldHidden: Boolean = false,
)

data class ActionFormFieldOption(
    val key: String,
    val value: String,
)