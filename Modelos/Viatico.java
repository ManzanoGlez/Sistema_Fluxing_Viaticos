package manzano.utj.sistemafluxing.Modelos;

public class Viatico {

    private String ID_Empleado, ID_Proyecto,
            ID_Viaje, Fecha_salida,
            Fecha_Regreso, Duracion, Destino,
            Motivo, Status, Gasolina, Gasolina_Comprobado,
            Autobus, Autobus_Comprobado,
            Avion, Avion_Comprobado,
            Uber, Uber_Comprobado,
            Alimentos, Alimentos_Comprobado,
            Casetas, Casetas_Comprobado,
            Hospedaje, Hospedaje_Comprobado,
            otros, otros_Comprobado,
            Total, Total_Comprobado;
    public static int ID_Viatico;




    /*
    *
     SELECT Vv.Id_Viatico,V.ID_Viaje,
    (SELECT CONCAT(Nombres, ' ', Apellidos) As Empleado FROM Empleado where Id_Empleado = V.ID_Empleado) AS Empleado,
    (SELECT CONCAT(Nombre_Proyecto, ' ', Codigo) AS Proyecto From Proyecto where ID_Proyecto = V.ID_Proyecto) AS Proyecto,
     Vv.Total_Viaticos,Gasolina,Autobus,Avion,UBER,Alimentos,Casetas,Hospedaje,
     Otros,convert(varchar, Fecha_salida, 101) as Fecha_salida,convert(varchar, Fecha_Regreso, 101) as Fecha_Regreso,
     Duracion,Destino,Motivo,V.Estatus,Total_Viaticos_Comprobados,Gasolina_Comprobados,Autobus_Comprobados,Avion_Comprobados,
     UBER_Comprobados,Alimentos_Comprobados,Casetas_Comprobados,Hospedaje_Comprobados,Otros_Comprobados
     FROM Viatico Vv JOIN Viaje V ON V.id_viaje = Vv.id_viaje
     WHERE V.ID_Empleado = 7
    *
    * */

    public static String Query_Administracion_Viaticos = "SELECT Vv.Id_Viatico,(SELECT CONCAT(Nombres, ' ', Apellidos) As Empleado FROM Empleado where Id_Empleado = V.ID_Empleado) AS Empleado,(SELECT CONCAT(Nombre_Proyecto, ' ', Codigo) AS Proyecto From Proyecto where ID_Proyecto = V.ID_Proyecto) AS Proyecto,Fecha_salida,Fecha_Regreso,Destino,Vv.Total_Viaticos,V.Estatus FROM Viatico Vv JOIN Viaje V ON V.id_viaje = Vv.id_viaje";

    public static String Query_Solicitud_Viaticos = "exec [sp_Alta_Viaje_Viaticos] ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";

    public static String Query_Carga_Viaticos_por_id = "SELECT Vv.Id_Viatico,V.ID_Viaje,(SELECT CONCAT(Nombres, ' ', Apellidos) As Empleado FROM Empleado where Id_Empleado = V.ID_Empleado) AS Empleado, (SELECT CONCAT(Nombre_Proyecto, ' ', Codigo) AS Proyecto From Proyecto where ID_Proyecto = V.ID_Proyecto) AS Proyecto,Vv.Total_Viaticos,Gasolina,Autobus,Avion,UBER,Alimentos,Casetas,Hospedaje, Otros,convert(varchar, Fecha_salida, 101) as Fecha_salida,convert(varchar, Fecha_Regreso, 101) as Fecha_Regreso,Duracion,Destino,Motivo,V.Estatus,Total_Viaticos_Comprobados,Gasolina_Comprobados,Autobus_Comprobados,Avion_Comprobados,UBER_Comprobados,Alimentos_Comprobados,Casetas_Comprobados,Hospedaje_Comprobados,Otros_Comprobados FROM Viatico Vv JOIN Viaje V ON V.id_viaje = Vv.id_viaje WHERE ID_Viatico = ?";

    public static String Query_Cambiar_Status = "UPDATE VIAJE SET Estatus = ?  WHERE ID_Viaje = ?";

    public static String Query_Captura_Factura = "exec [sp_Captura_Factura] ?,?,?,?,?,?,?,?,?,?,?,?;";

    public Viatico(String ID_Viaje, String ID_Empleado, String ID_Proyecto, String Fecha_salida, String Fecha_Regreso, String Destino, String Total, String Status) {
        this.ID_Viaje = ID_Viaje;
        this.ID_Empleado = ID_Empleado;
        this.ID_Proyecto = ID_Proyecto;
        this.Fecha_salida = Fecha_salida;
        this.Fecha_Regreso = Fecha_Regreso;
        this.Destino = Destino;
        this.Total = Total;
        this.Status = Status;
    }

    public String getID_Empleado() {
        return ID_Empleado;
    }

    public void setID_Empleado(String ID_Empleado) {
        this.ID_Empleado = ID_Empleado;
    }

    public String getID_Proyecto() {
        return ID_Proyecto;
    }

    public void setID_Proyecto(String ID_Proyecto) {
        this.ID_Proyecto = ID_Proyecto;
    }

    public String getFecha_salida() {
        return Fecha_salida;
    }

    public void setFecha_salida(String Fecha_salida) {
        this.Fecha_salida = Fecha_salida;
    }

    public String getFecha_Regreso() {
        return Fecha_Regreso;
    }

    public void setFecha_Regreso(String Fecha_Regreso) {
        this.Fecha_Regreso = Fecha_Regreso;
    }

    public String getDuracion() {
        return Duracion;
    }

    public void setDuracion(String Duracion) {
        this.Duracion = Duracion;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String Destino) {
        this.Destino = Destino;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String Motivo) {
        this.Motivo = Motivo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getID_Viaje() {
        return ID_Viaje;
    }

    public void setID_Viaje(String ID_Viaje) {
        this.ID_Viaje = ID_Viaje;
    }

    public String getGasolina() {
        return Gasolina;
    }

    public void setGasolina(String Gasolina) {
        this.Gasolina = Gasolina;
    }

    public String getGasolina_Comprobado() {
        return Gasolina_Comprobado;
    }

    public void setGasolina_Comprobado(String Gasolina_Comprobado) {
        this.Gasolina_Comprobado = Gasolina_Comprobado;
    }

    public String getAutobus() {
        return Autobus;
    }

    public void setAutobus(String Autobus) {
        this.Autobus = Autobus;
    }

    public String getAutobus_Comprobado() {
        return Autobus_Comprobado;
    }

    public void setAutobus_Comprobado(String Autobus_Comprobado) {
        this.Autobus_Comprobado = Autobus_Comprobado;
    }

    public String getAvion() {
        return Avion;
    }

    public void setAvion(String Avion) {
        this.Avion = Avion;
    }

    public String getAvion_Comprobado() {
        return Avion_Comprobado;
    }

    public void setAvion_Comprobado(String Avion_Comprobado) {
        this.Avion_Comprobado = Avion_Comprobado;
    }

    public String getUber() {
        return Uber;
    }

    public void setUber(String Uber) {
        this.Uber = Uber;
    }

    public String getUber_Comprobado() {
        return Uber_Comprobado;
    }

    public void setUber_Comprobado(String Uber_Comprobado) {
        this.Uber_Comprobado = Uber_Comprobado;
    }

    public String getAlimentos() {
        return Alimentos;
    }

    public void setAlimentos(String Alimentos) {
        this.Alimentos = Alimentos;
    }

    public String getAlimentos_Comprobado() {
        return Alimentos_Comprobado;
    }

    public void setAlimentos_Comprobado(String Alimentos_Comprobado) {
        this.Alimentos_Comprobado = Alimentos_Comprobado;
    }

    public String getCasetas() {
        return Casetas;
    }

    public void setCasetas(String Casetas) {
        this.Casetas = Casetas;
    }

    public String getCasetas_Comprobado() {
        return Casetas_Comprobado;
    }

    public void setCasetas_Comprobado(String Casetas_Comprobado) {
        this.Casetas_Comprobado = Casetas_Comprobado;
    }

    public String getHospedaje() {
        return Hospedaje;
    }

    public void setHospedaje(String Hospedaje) {
        this.Hospedaje = Hospedaje;
    }

    public String getHospedaje_Comprobado() {
        return Hospedaje_Comprobado;
    }

    public void setHospedaje_Comprobado(String Hospedaje_Comprobado) {
        this.Hospedaje_Comprobado = Hospedaje_Comprobado;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getOtros_Comprobado() {
        return otros_Comprobado;
    }

    public void setOtros_Comprobado(String otros_Comprobado) {
        this.otros_Comprobado = otros_Comprobado;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }

    public String getTotal_Comprobado() {
        return Total_Comprobado;
    }

    public void setTotal_Comprobado(String Total_Comprobado) {
        this.Total_Comprobado = Total_Comprobado;
    }

    public static String getQuery_Administracion_Viaticos() {
        return Query_Administracion_Viaticos;
    }

    public static void setQuery_Administracion_Viaticos(String Query_Administracion_Viaticos) {
        Viatico.Query_Administracion_Viaticos = Query_Administracion_Viaticos;
    }

    public static String getQuery_Solicitud_Viaticos() {
        return Query_Solicitud_Viaticos;
    }

    public static void setQuery_Solicitud_Viaticos(String Query_Solicitud_Viaticos) {
        Viatico.Query_Solicitud_Viaticos = Query_Solicitud_Viaticos;
    }


}
