<%@ attribute name="id" required="true"%>
<%@ attribute name="valor" required="false"%>
<input type="text" id="${id}" name="${id}" value="${valor}"/>
<script>
	$("#${id}").datepicker({
		dateFormat : 'dd/mm/yy'
	});
</script>