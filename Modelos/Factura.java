package manzano.utj.sistemafluxing.Modelos;

public class Factura {

    private int ID_Factura,IMG_Factuta;
    private String UUID,EMPLEADO, RUBRO, VERSION, FECHA, MONEDA, RFC_EMISOR, NOMBRE_EMISOR, RFC_RECEPTOR, NOMBRE_RECEPTOR,URL_FACTURA,MONTO;
    private double SUBTOTAL, IVA, TOTAL;

    public Factura() {

        this.setVERSION("");
        this.setFECHA("");
        this.setMONEDA("");
        this.setRFC_EMISOR("");
        this.setRFC_RECEPTOR("");
        this.setNOMBRE_RECEPTOR("");
        this.setSUBTOTAL(0f);
        this.setIVA(0f);
        this.setTOTAL(0f);

    }

    // cardView
    public Factura(String RFC_EMISOR, String RFC_RECEPTOR, String MONTO,String RUBRO) {
        this.RFC_EMISOR = RFC_EMISOR;
        this.RFC_RECEPTOR = RFC_RECEPTOR;
        this.MONTO = MONTO;
        this.RUBRO = RUBRO;
    }


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getMONTO() {
        return MONTO;
    }

    public void setMONTO(String MONTO) {
        this.MONTO = MONTO;
    }

    public int getID_Factura() {
        return ID_Factura;
    }

    public void setID_Factura(int ID_Factura) {
        this.ID_Factura = ID_Factura;
    }

    public String getURL_FACTURA() {
        return URL_FACTURA;
    }

    public void setURL_FACTURA(String URl_FACTURA) {
        this.URL_FACTURA = URl_FACTURA;
    }

    public int getIMG_Factuta() {  return IMG_Factuta; }

    public void setIMG_Factuta(int IMG_Factuta) { this.IMG_Factuta = IMG_Factuta;}

    public String getEMPLEADO() {
        return EMPLEADO;
    }

    public void setEMPLEADO(String EMPLEADO) {
        this.EMPLEADO = EMPLEADO;
    }

    public String getRUBRO() {
        return RUBRO;
    }

    public void setRUBRO(String RUBRO) {
        this.RUBRO = RUBRO;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getMONEDA() {
        return MONEDA;
    }

    public void setMONEDA(String MONEDA) {
        this.MONEDA = MONEDA;
    }

    public String getRFC_EMISOR() {
        return RFC_EMISOR;
    }

    public void setRFC_EMISOR(String RFC_EMISOR) {
        this.RFC_EMISOR = RFC_EMISOR;
    }

    public String getNOMBRE_EMISOR() {
        return NOMBRE_EMISOR;
    }

    public void setNOMBRE_EMISOR(String NOMBRE_EMISOR) {
        this.NOMBRE_EMISOR = NOMBRE_EMISOR;
    }

    public String getRFC_RECEPTOR() {
        return RFC_RECEPTOR;
    }

    public void setRFC_RECEPTOR(String RFC_RECEPTOR) {
        this.RFC_RECEPTOR = RFC_RECEPTOR;
    }

    public String getNOMBRE_RECEPTOR() {
        return NOMBRE_RECEPTOR;
    }

    public void setNOMBRE_RECEPTOR(String NOMBRE_RECEPTOR) {
        this.NOMBRE_RECEPTOR = NOMBRE_RECEPTOR;
    }

    public double getSUBTOTAL() {
        return SUBTOTAL;
    }

    public void setSUBTOTAL(double SUBTOTAL) {
        this.SUBTOTAL = SUBTOTAL;
    }

    public double getIVA() {
        return IVA;
    }

    public void setIVA(double IVA) {
        this.IVA = IVA;
    }

    public double getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(double TOTAL) {
        this.TOTAL = TOTAL;
    }
}
