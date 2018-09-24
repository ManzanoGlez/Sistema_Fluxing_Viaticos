package manzano.utj.sistemafluxing.Modelos;

public class Proyecto {

    private int ID_Proyecto;
    private String Nombre_Proyecto, Vendedor, Codigo, Cliente, Fecha_Inicio, Fecha_Termino, Descripcion, Pais, Estado, Ciudad, Planta, Estatus;
    private double Precio, Anticipo;


    public static String Query_Tabla = "SELECT ID_proyecto,Codigo,Nombre_Proyecto,Cliente,(select CONCAT(Nombres, ' ', Apellidos) FROM Empleado WHERE ID_EMPLEADO = P.Vendedor) as Vendedor,Fecha_Inicio,Estatus FROM PROYECTO P WHERE Estatus != 'Eliminado';";
    public static String Query_Delete = "exec [dbo].[sp_Baja_Proyecto] ?";
    public static String Query_Select_Cargar_Info = "SELECT (select CONCAT(Nombres, ' ', Apellidos) FROM Empleado WHERE ID_EMPLEADO = P.Vendedor) as Vendedor,Nombre_Proyecto,Codigo,Precio,Anticipo,Cliente,convert(varchar, Fecha_Inicio, 101),convert(varchar, Fecha_Termino, 101),Descripcion,Pais,Estado,Ciudad,Planta,Estatus FROM PROYECTO P WHERE ID_proyecto = ?";
    public static String Query_Select_Detalle_Where = "SELECT Nombre_Proyecto,Codigo,convert(varchar, Fecha_Inicio, 101),convert(varchar, Fecha_Termino, 101) FROM PROYECTO P WHERE ID_proyecto = ?";
    public static String Query_Listar_Proyectos = "SELECT CONCAT(Nombre_Proyecto,' - ',Codigo) FROM PROYECTO P WHERE estatus != 'Eliminado';";



    //Completo
    public Proyecto(int ID_Proyecto, String Vendedor, String Nombre_Proyecto, String Codigo, String Cliente, String Fecha_Inicio, String Fecha_Termino, String Descripcion, String Pais, String Estado, String Ciudad, String Planta, String Estatus, double Precio, double Anticipo) {
        this.ID_Proyecto = ID_Proyecto;
        this.Vendedor = Vendedor;
        this.Nombre_Proyecto = Nombre_Proyecto;
        this.Codigo = Codigo;
        this.Cliente = Cliente;
        this.Fecha_Inicio = Fecha_Inicio;
        this.Fecha_Termino = Fecha_Termino;
        this.Descripcion = Descripcion;
        this.Pais = Pais;
        this.Estado = Estado;
        this.Ciudad = Ciudad;
        this.Planta = Planta;
        this.Estatus = Estatus;
        this.Precio = Precio;
        this.Anticipo = Anticipo;
    }

    //Tabla
    public Proyecto(int ID_Proyecto,String Codigo, String Nombre_Proyecto, String Cliente, String Vendedor, String Fecha_Inicio, String Estatus) {
        this.ID_Proyecto = ID_Proyecto;
        this.Codigo = Codigo;
        this.Nombre_Proyecto = Nombre_Proyecto;
        this.Vendedor = Vendedor;
        this.Cliente = Cliente;
        this.Fecha_Inicio = Fecha_Inicio;
        this.Estatus = Estatus;
    }


    public int getID_Proyecto() {
        return ID_Proyecto;
    }

    public void setID_Proyecto(int ID_Proyecto) {
        this.ID_Proyecto = ID_Proyecto;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String Vendedor) {
        this.Vendedor = Vendedor;
    }

    public String getNombre_Proyecto() {
        return Nombre_Proyecto;
    }

    public void setNombre_Proyecto(String Nombre_Proyecto) {
        this.Nombre_Proyecto = Nombre_Proyecto;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public String getFecha_Inicio() {
        return Fecha_Inicio;
    }

    public void setFecha_Inicio(String Fecha_Inicio) {
        this.Fecha_Inicio = Fecha_Inicio;
    }

    public String getFecha_Termino() {
        return Fecha_Termino;
    }

    public void setFecha_Termino(String Fecha_Termino) {
        this.Fecha_Termino = Fecha_Termino;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String Pais) {
        this.Pais = Pais;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String Ciudad) {
        this.Ciudad = Ciudad;
    }

    public String getPlanta() {
        return Planta;
    }

    public void setPlanta(String Planta) {
        this.Planta = Planta;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String Estatus) {
        this.Estatus = Estatus;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    public double getAnticipo() {
        return Anticipo;
    }

    public void setAnticipo(double Anticipo) {
        this.Anticipo = Anticipo;
    }

}
