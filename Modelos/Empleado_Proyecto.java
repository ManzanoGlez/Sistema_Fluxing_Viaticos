package manzano.utj.sistemafluxing.Modelos;

public class Empleado_Proyecto {

    private String Empleado, Codigo_proyecto, Proyecto, Fecha_asignacion, Descripcion, Estatus;

    public static String Query_Tabla = "SELECT (SELECT CONCAT(Nombres,' ', Apellidos) As Empleado FROM Empleado where Id_Empleado = EP.Id_Empleado) AS Empleado, "
            + "Fecha_Asignado,Razon FROM EMPLEADO_PROYECTO EP WHERE ID_PROYECTO =  ? AND Estatus = 'Activo'";

    public static String Query_Delete = "DELETE FROM EMPLEADO_PROYECTO WHERE ID_Proyecto = ? and ID_Empleado= ?;";

    public static String Query_Insert_Update = "exec [dbo].[sp_Asignar_Empleado_Proyecto] ?,?,?,?";

    public static String Query_Select_Where = "SELECT convert(varchar, Fecha_Asignado, 101),Razon FROM EMPLEADO_PROYECTO WHERE ID_Empleado =?";

    public static String Query_Empleados_Select_Where = "SELECT (select Concat(Nombres,' ',Apellidos)  from EMPLEADO  where ID_Empleado = ?),convert(varchar, Fecha_Asignado, 101),Razon FROM EMPLEADO_PROYECTO ep WHERE ID_Empleado = ?";

    public static String Query_Proyectos_Asignados = "SELECT (SELECT Codigo AS Proyecto From Proyecto where ID_Proyecto = EP.ID_Proyecto) AS Proyecto,"
            + "(SELECT Nombre_Proyecto AS Proyecto From Proyecto where ID_Proyecto = EP.ID_Proyecto) AS Proyecto,"
            + "Fecha_Asignado,Razon,Estatus FROM EMPLEADO_PROYECTO EP WHERE ID_EMPLEADO = ?";



    public Empleado_Proyecto(String Empleado, String Fecha_asignacion, String Descripcion) {
        this.Empleado = Empleado;
        this.Fecha_asignacion = Fecha_asignacion;
        this.Descripcion = Descripcion;
    }

    public Empleado_Proyecto(String Codigo_proyecto, String Proyecto, String Fecha_asignacion, String Descripcion, String Estatus) {
        this.Codigo_proyecto = Codigo_proyecto;
        this.Proyecto = Proyecto;
        this.Fecha_asignacion = Fecha_asignacion;
        this.Descripcion = Descripcion;
        this.Estatus = Estatus;
    }

    public String getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(String Empleado) {
        this.Empleado = Empleado;
    }

    public String getCodigo_proyecto() {
        return Codigo_proyecto;
    }

    public void setCodigo_proyecto(String Codigo_proyecto) {
        this.Codigo_proyecto = Codigo_proyecto;
    }

    public String getProyecto() {
        return Proyecto;
    }

    public void setProyecto(String Proyecto) {
        this.Proyecto = Proyecto;
    }

    public String getFecha_asignacion() {
        return Fecha_asignacion;
    }

    public void setFecha_asignacion(String Fecha_asignacion) {
        this.Fecha_asignacion = Fecha_asignacion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String Estatus) {
        this.Estatus = Estatus;
    }


}
