<%@ page import="grailsrest.Front" %>



<div class="fieldcontain ${hasErrors(bean: frontInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="front.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${frontInstance?.name}" />
</div>

<div class="fieldcontain ${hasErrors(bean: frontInstance, field: 'value', 'error')} ">
	<label for="value">
		<g:message code="front.value.label" default="Value" />
		
	</label>
	<g:textField name="value" value="${frontInstance?.value}" />
</div>

