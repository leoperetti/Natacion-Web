<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
boolean existe = <%= session.getAttribute("existeUsuario") %>;
if(!existe){
	alert("Error en el logueo")
}
else
{
	alert("Has sido logueado correctamente")
}

</script>
</head>
<body>

</body>
</html>