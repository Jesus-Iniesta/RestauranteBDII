package Vista;

import Modelo.Control.CarritoDeCompras;
import Modelo.Control.ListaPuesto;
import Modelo.Control.ListadoCategorias;
import Modelo.Control.ListadoProductos;
import Modelo.Control.ListadoProveedores;
import Modelo.Control.ProductoControlador;
import Modelo.DAO.*;
import Modelo.Entidades.*;
import Util.Conexion;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.math.BigDecimal;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author karen
 */
public class Sistema extends javax.swing.JFrame {
    
    private Connection conexion;
    private CarritoDeCompras carrito;
    private Venta venta;
    public Sistema() {
        initComponents();
    }
    
    public Sistema(Connection conexion){ //constructor para recibir conexión activa
        this.conexion = conexion;
        initComponents();
        cargarComboProducto(JcomboProductos);
        cargarComboProducto(CBProductosPdd);
        cargarComboPuestos(JcomboPuesto);
        cargarComboProveedor(JcomboProveedor);
        cargarComboCategorias(JComboCategorias);
        cargarTabla();
        cargarTablaProveedor();
        cargarTablaFactura();
        cargarTablaVenta(); 
        this.carrito = new CarritoDeCompras();
        this.venta = new Venta();
    }
    public void cargarTabla(){
        DefaultTableModel modeloTabla = (DefaultTableModel)TablaProducto.getModel();
        modeloTabla.setRowCount(0);
        
        int [] anchos = {5,150,100,70,70};
        for(int i = 0; i<TablaProducto.getColumnCount();i++){
            TablaProducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        
        ProductosDAO tabla = new ProductosDAO(conexion);
        tabla.obtenerTodosLosProductos(modeloTabla);
    }
    public void cargarTablaProveedor(){
        DefaultTableModel modeloTabla = (DefaultTableModel)TablaProvedor.getModel();
        modeloTabla.setRowCount(0);
        
        int [] anchos = {5,150,100,70};
        if (TablaProvedor.getColumnCount() == anchos.length) {
            for (int i = 0; i < TablaProvedor.getColumnCount(); i++) {
                TablaProvedor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }
        } else {
            System.out.println("El número de columnas no coincide con los anchos definidos.");
        }
        
        ProveedorDAO tablaProv = new ProveedorDAO(conexion);
        tablaProv.obtenerTodosLosProveedor(modeloTabla);
    }
    public void cargarTablaFactura(){
        DefaultTableModel modeloTabla = (DefaultTableModel)TablaFactura.getModel();
        modeloTabla.setRowCount(0);
        
        int [] anchos = {10,100,100,100,100,100,100};
        if(TablaFactura.getColumnCount() == anchos.length){
            for(int i = 0; i<TablaFactura.getColumnCount();i++ ){
                TablaFactura.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
        } else{
            System.out.println("El número de columnas no coincide con los anchos definidos");
        }
        FacturaDAO tableFact = new FacturaDAO(conexion);
        tableFact.obtenerTodasLasFacturas(modeloTabla);
    }
    public void cargarTablaVenta(){
        DefaultTableModel modeloTabla = (DefaultTableModel)TablaNV.getModel();
        modeloTabla.setRowCount(0);
        
        int [] anchos = {10,100,100,150,100,100};
        if(TablaNV.getColumnCount() == anchos.length){
            for(int i = 0; i<TablaNV.getColumnCount();i++ ){
                TablaNV.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
        } else{
            System.out.println("El número de columnas no coincide con los anchos definidos");
        }
        VentaDAO tbVenta = new VentaDAO(conexion);
        tbVenta.mostrarTablaVenta(modeloTabla);
    }
    
    public void cargarTablaVentasTotales(){
        DefaultTableModel modeloTabla = (DefaultTableModel)TablaVenta.getModel();
        modeloTabla.setRowCount(0);
        
        int [] anchos = {10,100,100,150,100,100,100,100};
        if(TablaVenta.getColumnCount() == anchos.length){
            for(int i = 0; i<TablaVenta.getColumnCount();i++ ){
                TablaVenta.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
        } else{
            System.out.println("El número de columnas no coincide con los anchos definidos");
        }
        VentaDAO tbVentas = new VentaDAO(conexion);
        tbVentas.obtenerTodasLasVentas(modeloTabla);
    }
    public void cargarTablaHsEmp(){
        DefaultTableModel modeloTabla = (DefaultTableModel)Tabla_hist_emp.getModel();
        modeloTabla.setRowCount(0);
        
        int [] anchos = {10,100,100,150,100};
        if(Tabla_hist_emp.getColumnCount() == anchos.length){
            for(int i = 0; i<Tabla_hist_emp.getColumnCount();i++ ){
                Tabla_hist_emp.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
        } else{
            System.out.println("El número de columnas no coincide con los anchos definidos");
        }
        HistorialEmpleadoDAO tabla = new  HistorialEmpleadoDAO(conexion);
        tabla.obtenerTodosLosHistoriales(modeloTabla);
    }
    public void cargarTablaHspProductos(){
        DefaultTableModel modeloTabla = (DefaultTableModel)Tabla_hist_pro.getModel();
        modeloTabla.setRowCount(0);
        
        int [] anchos = {10,100,100,150};
        if(Tabla_hist_pro.getColumnCount() == anchos.length){
            for(int i = 0; i<Tabla_hist_pro.getColumnCount();i++ ){
                Tabla_hist_pro.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }
        } else{
            System.out.println("El número de columnas no coincide con los anchos definidos");
        }
        HistorialProductoDAO tbHistorial = new HistorialProductoDAO(conexion);
        tbHistorial.obtenerTodosLosHistoriales(modeloTabla);
    }
    
    public void limpiarTablaProductos(){
        txtIDproducto.setText("");
        txtNombreProducto.setText("");
        txtPrecioProd.setText("");
        jSpinnStock.setValue(0);
        AreaDesc.setText("");
        JComboCategorias.setSelectedIndex(0);
        JcomboProveedor.setSelectedIndex(0);
    }
    public static void mostrarMensaje(JFrame parent, String mensaje, int duracion) {
        // Crear un JDialog para el mensaje
        JDialog dialog = new JDialog(parent, "Información", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(parent);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        // Agregar el mensaje al JDialog
        JLabel label = new JLabel(mensaje);
        dialog.add(label);

        // Crear un temporizador para cerrar el diálogo después de 'duracion' milisegundos
        Timer timer = new Timer(duracion, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();

        // Mostrar el JDialog
        dialog.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        NuevaVenta = new javax.swing.JButton();
        Clientes = new javax.swing.JButton();
        Provedor = new javax.swing.JButton();
        Pedidos = new javax.swing.JButton();
        Ventas = new javax.swing.JButton();
        Productos = new javax.swing.JButton();
        Factura = new javax.swing.JButton();
        Empleado = new javax.swing.JButton();
        Historial = new javax.swing.JButton();
        BtnCerrarSesion = new javax.swing.JButton();
        ImagenPrincipal = new javax.swing.JLabel();
        PanelGeneral = new javax.swing.JTabbedPane();
        PanelNuevaVenta = new javax.swing.JPanel();
        DescuentoNV = new javax.swing.JLabel();
        CantidadNV = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaNV = new javax.swing.JTable();
        BtnPagarVenta = new javax.swing.JButton();
        BtnEliminarNV = new javax.swing.JButton();
        BtnLimpiarNV = new javax.swing.JButton();
        JcomboDescuento = new javax.swing.JComboBox<>();
        Producto = new javax.swing.JLabel();
        JcomboCantidad = new javax.swing.JComboBox<>();
        JcomboProductos = new javax.swing.JComboBox<>();
        btnAñadirProductos = new javax.swing.JButton();
        MetodoDePago = new javax.swing.JLabel();
        jComboMetodoDePago = new javax.swing.JComboBox<>();
        MetodoDePago1 = new javax.swing.JLabel();
        txtPrecioUVenta = new javax.swing.JTextField();
        MetodoDePago2 = new javax.swing.JLabel();
        txtTotalVenta = new javax.swing.JTextField();
        btnConsultarCarrito = new javax.swing.JButton();
        PanelProvedor = new javax.swing.JPanel();
        NombrePrvd = new javax.swing.JLabel();
        DireccionPrvd = new javax.swing.JLabel();
        TelefonoPrvd = new javax.swing.JLabel();
        txtNombreProv = new javax.swing.JTextField();
        txtCalleProvedor = new javax.swing.JTextField();
        txtTelefonoProv = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaProvedor = new javax.swing.JTable();
        BtnGuardarPrvd = new javax.swing.JButton();
        BtnActualizarPrvd = new javax.swing.JButton();
        BtnEliminarPrvd = new javax.swing.JButton();
        DireccionPrvd1 = new javax.swing.JLabel();
        DireccionPrvd2 = new javax.swing.JLabel();
        DireccionPrvd3 = new javax.swing.JLabel();
        txtPaisProv = new javax.swing.JTextField();
        txtCodigoPProv = new javax.swing.JTextField();
        txtColoniaProveedor = new javax.swing.JTextField();
        BtnLimpiarProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        PanelPedido = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TablaPedido = new javax.swing.JTable();
        PrecioPdd = new javax.swing.JLabel();
        CantidadPdd = new javax.swing.JLabel();
        ProductosPdd = new javax.swing.JLabel();
        MetododePagoPdd = new javax.swing.JLabel();
        TFPrecioPdd = new javax.swing.JTextField();
        CBProductosPdd = new javax.swing.JComboBox<>();
        CBMetododePagoPdd = new javax.swing.JComboBox<>();
        BtnNuevoPdd = new javax.swing.JButton();
        BtnActualizarPdd = new javax.swing.JButton();
        BtnEliminarPdd = new javax.swing.JButton();
        BtnConsultarPdd = new javax.swing.JButton();
        JcomboCantidadDoc = new javax.swing.JComboBox<>();
        btnAñadirProductos1 = new javax.swing.JButton();
        btnConsultarCarrito1 = new javax.swing.JButton();
        DescuentoNV1 = new javax.swing.JLabel();
        JcomboDescuento1 = new javax.swing.JComboBox<>();
        MetodoDePago3 = new javax.swing.JLabel();
        txtTotalVenta1 = new javax.swing.JTextField();
        PanelProducto = new javax.swing.JPanel();
        NombrePrd = new javax.swing.JLabel();
        PrecioPrd = new javax.swing.JLabel();
        StockPrd = new javax.swing.JLabel();
        ProvedorPrd = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        txtPrecioProd = new javax.swing.JTextField();
        JcomboProveedor = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaProducto = new javax.swing.JTable();
        btnGuardarProducto = new javax.swing.JButton();
        BtnActualizarPrd = new javax.swing.JButton();
        BtnEliminarPrd = new javax.swing.JButton();
        BtnConsultarPrd = new javax.swing.JButton();
        BtnDescripcionPrd = new javax.swing.JLabel();
        AreaDesc = new java.awt.TextArea();
        BtnCategoriaPrd = new javax.swing.JLabel();
        JComboCategorias = new javax.swing.JComboBox<>();
        jSpinnStock = new javax.swing.JSpinner();
        btnLimpiar = new javax.swing.JButton();
        txtIDproducto = new javax.swing.JTextField();
        PanelVenta = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaVenta = new javax.swing.JTable();
        BtnVerTabla = new javax.swing.JButton();
        PanelHistorial = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        Tabla_hist_emp = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        Tabla_hist_pro = new javax.swing.JTable();
        Hist_emp = new javax.swing.JLabel();
        Hist_product = new javax.swing.JLabel();
        btnActHs = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TablaFactura = new javax.swing.JTable();
        BtnNuevoFct = new javax.swing.JButton();
        BtnEliminarFct = new javax.swing.JButton();
        BtnActualizarFct = new javax.swing.JButton();
        RFCFct = new javax.swing.JLabel();
        NombreFct = new javax.swing.JLabel();
        EmisorFct = new javax.swing.JLabel();
        TFIDFct = new javax.swing.JTextField();
        TxtFRFCFct = new javax.swing.JTextField();
        TxtFNombreFct = new javax.swing.JTextField();
        jButton33 = new javax.swing.JButton();
        txtIdFactura = new javax.swing.JTextField();
        JComboEmisores = new javax.swing.JComboBox<>();
        EmisorFct1 = new javax.swing.JLabel();
        txtId_pedido = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtNombreEmp = new javax.swing.JTextField();
        txtApellidoPEmp = new javax.swing.JTextField();
        txtApellidoMEmp = new javax.swing.JTextField();
        txtTelEmp = new javax.swing.JTextField();
        txtRFCEmp = new javax.swing.JTextField();
        txtCurpEmp = new javax.swing.JTextField();
        txtSalarioEmp = new javax.swing.JTextField();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        JcomboPuesto = new javax.swing.JComboBox<>();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        JcomboDia = new javax.swing.JComboBox<>();
        JcomboJornada = new javax.swing.JComboBox<>();
        BtnAgregar = new javax.swing.JButton();
        PanelCliente = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaClientes = new javax.swing.JTable();
        NombreClnt = new javax.swing.JLabel();
        TFApMaternoClnt = new javax.swing.JTextField();
        ApellidoMaternoClnt = new javax.swing.JLabel();
        ApellidoPaternoClnt = new javax.swing.JLabel();
        TelefonoClnt = new javax.swing.JLabel();
        DIreccionClnt = new javax.swing.JLabel();
        CorreoClnt = new javax.swing.JLabel();
        TFTelefonoClnt = new javax.swing.JTextField();
        TFCorreoClnt = new javax.swing.JTextField();
        TFDireccionClnt = new javax.swing.JTextField();
        TFApPaternoClnt = new javax.swing.JTextField();
        TFNombreClnt = new javax.swing.JTextField();
        BtnGuardarClnt = new javax.swing.JButton();
        BtnEliminarClnt = new javax.swing.JButton();
        BtnActualizarClnt = new javax.swing.JButton();
        BtnConsultarClnt = new javax.swing.JButton();
        DIreccionClnt1 = new javax.swing.JLabel();
        DIreccionClnt2 = new javax.swing.JLabel();
        DIreccionClnt3 = new javax.swing.JLabel();
        TFDireccionClnt1 = new javax.swing.JTextField();
        TFDireccionClnt2 = new javax.swing.JTextField();
        TFDireccionClnt3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 0, 51));

        NuevaVenta.setBackground(new java.awt.Color(228, 182, 44));
        NuevaVenta.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        NuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoventa (1).png"))); // NOI18N
        NuevaVenta.setText("Nueva Venta");
        NuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaVentaActionPerformed(evt);
            }
        });

        Clientes.setBackground(new java.awt.Color(228, 182, 44));
        Clientes.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoclientes (1).png"))); // NOI18N
        Clientes.setText("Clientes");
        Clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientesActionPerformed(evt);
            }
        });

        Provedor.setBackground(new java.awt.Color(228, 182, 44));
        Provedor.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Provedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/provedoooor (1).png"))); // NOI18N
        Provedor.setText("Provedor");
        Provedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProvedorActionPerformed(evt);
            }
        });

        Pedidos.setBackground(new java.awt.Color(228, 182, 44));
        Pedidos.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Pedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconopedidos.png"))); // NOI18N
        Pedidos.setText("Pedidos");

        Ventas.setBackground(new java.awt.Color(228, 182, 44));
        Ventas.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/historialdeventas1 (1).png"))); // NOI18N
        Ventas.setText("Ventas");
        Ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentasActionPerformed(evt);
            }
        });

        Productos.setBackground(new java.awt.Color(228, 182, 44));
        Productos.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconoproductos (1).png"))); // NOI18N
        Productos.setText("Productos");

        Factura.setBackground(new java.awt.Color(228, 182, 44));
        Factura.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Factura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/facturaicono (1) (1).png"))); // NOI18N
        Factura.setText("Factura");
        Factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FacturaActionPerformed(evt);
            }
        });

        Empleado.setBackground(new java.awt.Color(228, 182, 44));
        Empleado.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Empleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/persona (3).png"))); // NOI18N
        Empleado.setText("Empleado");
        Empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpleadoActionPerformed(evt);
            }
        });

        Historial.setBackground(new java.awt.Color(228, 182, 44));
        Historial.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Historial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/historialicono (1).png"))); // NOI18N
        Historial.setText("Historial");
        Historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistorialActionPerformed(evt);
            }
        });

        BtnCerrarSesion.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        BtnCerrarSesion.setForeground(new java.awt.Color(102, 51, 0));
        BtnCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salida (1).jpg"))); // NOI18N
        BtnCerrarSesion.setText("Cerrar sesión");
        BtnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Factura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Historial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Ventas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Productos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Empleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Pedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Provedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(NuevaVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BtnCerrarSesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Clientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Provedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Pedidos)
                .addGap(12, 12, 12)
                .addComponent(Empleado)
                .addGap(12, 12, 12)
                .addComponent(Productos)
                .addGap(12, 12, 12)
                .addComponent(Ventas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Historial)
                .addGap(12, 12, 12)
                .addComponent(Factura)
                .addGap(53, 53, 53)
                .addComponent(BtnCerrarSesion)
                .addGap(95, 95, 95))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 140, 580));

        ImagenPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoooestaa.png"))); // NOI18N
        getContentPane().add(ImagenPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 150));

        DescuentoNV.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DescuentoNV.setText("Descuento");

        CantidadNV.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        CantidadNV.setText("Cantidad");

        TablaNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Fecha", "Cantidad", "Productos", "Precio", "Total"
            }
        ));
        jScrollPane2.setViewportView(TablaNV);
        if (TablaNV.getColumnModel().getColumnCount() > 0) {
            TablaNV.getColumnModel().getColumn(0).setPreferredWidth(15);
            TablaNV.getColumnModel().getColumn(1).setPreferredWidth(50);
            TablaNV.getColumnModel().getColumn(2).setPreferredWidth(30);
            TablaNV.getColumnModel().getColumn(4).setPreferredWidth(15);
            TablaNV.getColumnModel().getColumn(5).setPreferredWidth(25);
        }

        BtnPagarVenta.setText("Pagar");
        BtnPagarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPagarVentaActionPerformed(evt);
            }
        });

        BtnEliminarNV.setText("Eliminar");
        BtnEliminarNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarNVActionPerformed(evt);
            }
        });

        BtnLimpiarNV.setText("Limpiar");
        BtnLimpiarNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarNVActionPerformed(evt);
            }
        });

        JcomboDescuento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0%", "5%", "10%", "15%", "20%", "30%" }));
        JcomboDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcomboDescuentoActionPerformed(evt);
            }
        });

        Producto.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Producto.setText("Producto");

        JcomboCantidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        JcomboCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcomboCantidadActionPerformed(evt);
            }
        });

        JcomboProductos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JcomboProductosItemStateChanged(evt);
            }
        });
        JcomboProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcomboProductosActionPerformed(evt);
            }
        });

        btnAñadirProductos.setText("Añadir");
        btnAñadirProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirProductosActionPerformed(evt);
            }
        });

        MetodoDePago.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        MetodoDePago.setText("Metodo de pago");

        jComboMetodoDePago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Transferencia", "Efectivo", "Tarjeta" }));
        jComboMetodoDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMetodoDePagoActionPerformed(evt);
            }
        });

        MetodoDePago1.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        MetodoDePago1.setText("Total");

        txtPrecioUVenta.setEditable(false);
        txtPrecioUVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioUVentaActionPerformed(evt);
            }
        });

        MetodoDePago2.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        MetodoDePago2.setText("Precio");

        txtTotalVenta.setEditable(false);
        txtTotalVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalVentaActionPerformed(evt);
            }
        });

        btnConsultarCarrito.setText("Carrito");
        btnConsultarCarrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarCarritoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelNuevaVentaLayout = new javax.swing.GroupLayout(PanelNuevaVenta);
        PanelNuevaVenta.setLayout(PanelNuevaVentaLayout);
        PanelNuevaVentaLayout.setHorizontalGroup(
            PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelNuevaVentaLayout.createSequentialGroup()
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CantidadNV)
                                    .addComponent(Producto, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                        .addComponent(JcomboCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(JcomboProductos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelNuevaVentaLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                        .addComponent(DescuentoNV)
                                        .addGap(18, 18, 18)
                                        .addComponent(JcomboDescuento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                        .addComponent(MetodoDePago)
                                        .addGap(30, 30, 30)
                                        .addComponent(jComboMetodoDePago, 0, 117, Short.MAX_VALUE)))))
                        .addGap(53, 53, 53))
                    .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                        .addComponent(MetodoDePago1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                            .addComponent(MetodoDePago2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtPrecioUVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                            .addGap(17, 17, 17)
                                            .addComponent(btnAñadirProductos)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnConsultarCarrito)))))
                            .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtnLimpiarNV, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                                        .addComponent(BtnPagarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(BtnEliminarNV, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        PanelNuevaVentaLayout.setVerticalGroup(
            PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JcomboProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Producto))
                        .addGap(18, 18, 18)
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CantidadNV)
                            .addComponent(JcomboCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConsultarCarrito)
                            .addComponent(btnAñadirProductos, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MetodoDePago2)
                            .addComponent(txtPrecioUVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MetodoDePago)
                            .addComponent(jComboMetodoDePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DescuentoNV)
                            .addComponent(JcomboDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MetodoDePago1)
                            .addComponent(txtTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(PanelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnEliminarNV)
                            .addComponent(BtnPagarVenta))
                        .addGap(38, 38, 38)
                        .addComponent(BtnLimpiarNV))
                    .addGroup(PanelNuevaVentaLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        PanelGeneral.addTab("NV", PanelNuevaVenta);

        NombrePrvd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        NombrePrvd.setText("Nombre");

        DireccionPrvd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DireccionPrvd.setText("Calle");

        TelefonoPrvd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        TelefonoPrvd.setText("Telefono");

        TablaProvedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Direccion", "Telefono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProvedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaProvedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablaProvedor);
        if (TablaProvedor.getColumnModel().getColumnCount() > 0) {
            TablaProvedor.getColumnModel().getColumn(0).setPreferredWidth(40);
            TablaProvedor.getColumnModel().getColumn(1).setPreferredWidth(100);
            TablaProvedor.getColumnModel().getColumn(2).setPreferredWidth(80);
            TablaProvedor.getColumnModel().getColumn(3).setPreferredWidth(70);
        }

        BtnGuardarPrvd.setText("Guardar");
        BtnGuardarPrvd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarPrvdActionPerformed(evt);
            }
        });

        BtnActualizarPrvd.setText("Actualizar");
        BtnActualizarPrvd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarPrvdActionPerformed(evt);
            }
        });

        BtnEliminarPrvd.setText("Eliminar");
        BtnEliminarPrvd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarPrvdActionPerformed(evt);
            }
        });

        DireccionPrvd1.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DireccionPrvd1.setText("Colonia");

        DireccionPrvd2.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DireccionPrvd2.setText("Pais");

        DireccionPrvd3.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DireccionPrvd3.setText("Codigo Postal");

        txtCodigoPProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoPProvActionPerformed(evt);
            }
        });

        BtnLimpiarProveedor.setText("Limpiar");
        BtnLimpiarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarProveedorActionPerformed(evt);
            }
        });

        txtIdProveedor.setEditable(false);
        txtIdProveedor.setEnabled(false);

        javax.swing.GroupLayout PanelProvedorLayout = new javax.swing.GroupLayout(PanelProvedor);
        PanelProvedor.setLayout(PanelProvedorLayout);
        PanelProvedorLayout.setHorizontalGroup(
            PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelProvedorLayout.createSequentialGroup()
                .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelProvedorLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(PanelProvedorLayout.createSequentialGroup()
                                    .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(NombrePrvd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(DireccionPrvd, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                        .addComponent(TelefonoPrvd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCalleProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNombreProv, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTelefonoProv, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPaisProv, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(PanelProvedorLayout.createSequentialGroup()
                                    .addComponent(BtnGuardarPrvd, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BtnLimpiarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(PanelProvedorLayout.createSequentialGroup()
                                    .addComponent(BtnEliminarPrvd, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BtnActualizarPrvd))
                                .addGroup(PanelProvedorLayout.createSequentialGroup()
                                    .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(DireccionPrvd1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(DireccionPrvd2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(txtColoniaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelProvedorLayout.createSequentialGroup()
                                .addComponent(DireccionPrvd3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodigoPProv, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelProvedorLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        PanelProvedorLayout.setVerticalGroup(
            PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelProvedorLayout.createSequentialGroup()
                .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelProvedorLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreProv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NombrePrvd))
                        .addGap(18, 18, 18)
                        .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DireccionPrvd)
                            .addComponent(txtCalleProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DireccionPrvd1)
                            .addComponent(txtColoniaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DireccionPrvd2)
                            .addComponent(txtPaisProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DireccionPrvd3)
                            .addComponent(txtCodigoPProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TelefonoPrvd)
                            .addComponent(txtTelefonoProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnGuardarPrvd)
                            .addComponent(BtnLimpiarProveedor))
                        .addGap(18, 18, 18)
                        .addGroup(PanelProvedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnEliminarPrvd)
                            .addComponent(BtnActualizarPrvd))
                        .addGap(34, 34, 34)
                        .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelProvedorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        PanelGeneral.addTab("Prov", PanelProvedor);

        TablaPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Precio", "Cantidad", "Tipo", "Productos", "Metodo de Pago"
            }
        ));
        jScrollPane6.setViewportView(TablaPedido);

        PrecioPdd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        PrecioPdd.setText("Precio");

        CantidadPdd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        CantidadPdd.setText("Cantidad");

        ProductosPdd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        ProductosPdd.setText("Productos");

        MetododePagoPdd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        MetododePagoPdd.setText("Metodo de pago");

        TFPrecioPdd.setEditable(false);

        CBProductosPdd.setEditable(true);

        CBMetododePagoPdd.setEditable(true);
        CBMetododePagoPdd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Transferencia", "Efectivo", "Tarjeta" }));

        BtnNuevoPdd.setText("Nuevo");
        BtnNuevoPdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoPddActionPerformed(evt);
            }
        });

        BtnActualizarPdd.setText("Actualizar");

        BtnEliminarPdd.setText("Eliminar");

        BtnConsultarPdd.setText("Consultar");
        BtnConsultarPdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConsultarPddActionPerformed(evt);
            }
        });

        JcomboCantidadDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        JcomboCantidadDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcomboCantidadDocActionPerformed(evt);
            }
        });

        btnAñadirProductos1.setText("Añadir");
        btnAñadirProductos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirProductos1ActionPerformed(evt);
            }
        });

        btnConsultarCarrito1.setText("Carrito");
        btnConsultarCarrito1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarCarrito1ActionPerformed(evt);
            }
        });

        DescuentoNV1.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DescuentoNV1.setText("Descuento");

        JcomboDescuento1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0%", "5%", "10%", "15%", "20%", "30%" }));
        JcomboDescuento1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcomboDescuento1ActionPerformed(evt);
            }
        });

        MetodoDePago3.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        MetodoDePago3.setText("Total");

        txtTotalVenta1.setEditable(false);
        txtTotalVenta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalVenta1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelPedidoLayout = new javax.swing.GroupLayout(PanelPedido);
        PanelPedido.setLayout(PanelPedidoLayout);
        PanelPedidoLayout.setHorizontalGroup(
            PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPedidoLayout.createSequentialGroup()
                .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPedidoLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(CantidadPdd, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ProductosPdd, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(btnAñadirProductos1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConsultarCarrito1)
                            .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(CBProductosPdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(JcomboCantidadDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44))
                    .addGroup(PanelPedidoLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(BtnActualizarPdd)
                            .addGroup(PanelPedidoLayout.createSequentialGroup()
                                .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BtnNuevoPdd, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtnEliminarPdd))
                                .addGap(39, 39, 39)
                                .addComponent(BtnConsultarPdd, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelPedidoLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPedidoLayout.createSequentialGroup()
                                .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(PanelPedidoLayout.createSequentialGroup()
                                        .addComponent(PrecioPdd, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(TFPrecioPdd, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelPedidoLayout.createSequentialGroup()
                                        .addComponent(MetododePagoPdd, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(CBMetododePagoPdd, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(44, 44, 44))
                            .addGroup(PanelPedidoLayout.createSequentialGroup()
                                .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DescuentoNV1)
                                    .addComponent(MetodoDePago3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelPedidoLayout.createSequentialGroup()
                                        .addComponent(txtTotalVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(JcomboDescuento1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );
        PanelPedidoLayout.setVerticalGroup(
            PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPedidoLayout.createSequentialGroup()
                .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelPedidoLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBProductosPdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductosPdd))
                        .addGap(30, 30, 30)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CantidadPdd)
                            .addComponent(JcomboCantidadDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAñadirProductos1)
                            .addComponent(btnConsultarCarrito1))
                        .addGap(19, 19, 19)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PrecioPdd)
                            .addComponent(TFPrecioPdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CBMetododePagoPdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MetododePagoPdd))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DescuentoNV1)
                            .addComponent(JcomboDescuento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MetodoDePago3)
                            .addComponent(txtTotalVenta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnActualizarPdd)
                            .addComponent(BtnNuevoPdd))
                        .addGap(32, 32, 32)
                        .addGroup(PanelPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnEliminarPdd)
                            .addComponent(BtnConsultarPdd)))
                    .addGroup(PanelPedidoLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        PanelGeneral.addTab("Pedido domicilio", PanelPedido);

        NombrePrd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        NombrePrd.setText("Nombre");

        PrecioPrd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        PrecioPrd.setText("Precio");

        StockPrd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        StockPrd.setText("Stock");

        ProvedorPrd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        ProvedorPrd.setText("Provedor");

        txtPrecioProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioProdActionPerformed(evt);
            }
        });

        JcomboProveedor.setEditable(true);
        JcomboProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige" }));

        TablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Precio", "Stock", "Provedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TablaProducto);
        if (TablaProducto.getColumnModel().getColumnCount() > 0) {
            TablaProducto.getColumnModel().getColumn(0).setPreferredWidth(50);
            TablaProducto.getColumnModel().getColumn(1).setPreferredWidth(100);
            TablaProducto.getColumnModel().getColumn(2).setPreferredWidth(50);
            TablaProducto.getColumnModel().getColumn(3).setPreferredWidth(40);
            TablaProducto.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        btnGuardarProducto.setText("Guardar");
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });

        BtnActualizarPrd.setText("Actualizar");
        BtnActualizarPrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarPrdActionPerformed(evt);
            }
        });

        BtnEliminarPrd.setText("Eliminar");
        BtnEliminarPrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarPrdActionPerformed(evt);
            }
        });

        BtnConsultarPrd.setText("Consultar");

        BtnDescripcionPrd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        BtnDescripcionPrd.setText("Descripcion");

        AreaDesc.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        BtnCategoriaPrd.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        BtnCategoriaPrd.setText("Categoria");

        JComboCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige" }));
        JComboCategorias.setToolTipText("");
        JComboCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboCategoriasActionPerformed(evt);
            }
        });

        jSpinnStock.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        txtIDproducto.setEditable(false);
        txtIDproducto.setEnabled(false);

        javax.swing.GroupLayout PanelProductoLayout = new javax.swing.GroupLayout(PanelProducto);
        PanelProducto.setLayout(PanelProductoLayout);
        PanelProductoLayout.setHorizontalGroup(
            PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelProductoLayout.createSequentialGroup()
                .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelProductoLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGuardarProducto)
                            .addComponent(BtnEliminarPrd))
                        .addGap(18, 18, 18)
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnActualizarPrd)
                            .addComponent(BtnConsultarPrd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelProductoLayout.createSequentialGroup()
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelProductoLayout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ProvedorPrd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtnDescripcionPrd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(StockPrd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PrecioPrd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtnCategoriaPrd)
                                    .addComponent(NombrePrd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelProductoLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(txtIDproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnStock, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AreaDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtPrecioProd, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelProductoLayout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(JcomboProveedor, javax.swing.GroupLayout.Alignment.LEADING, 0, 165, Short.MAX_VALUE)
                                .addComponent(JComboCategorias, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        PanelProductoLayout.setVerticalGroup(
            PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelProductoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelProductoLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(PanelProductoLayout.createSequentialGroup()
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NombrePrd)
                            .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PrecioPrd)
                            .addComponent(txtPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(StockPrd)
                            .addComponent(jSpinnStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelProductoLayout.createSequentialGroup()
                                .addComponent(BtnDescripcionPrd)
                                .addGap(12, 12, 12)
                                .addComponent(txtIDproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(AreaDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JComboCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnCategoriaPrd))
                        .addGap(37, 37, 37)
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ProvedorPrd)
                            .addComponent(JcomboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelProductoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnGuardarProducto)
                                    .addComponent(BtnActualizarPrd))
                                .addGap(18, 18, 18)
                                .addGroup(PanelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BtnEliminarPrd)
                                    .addComponent(BtnConsultarPrd))
                                .addGap(33, 33, 33))
                            .addGroup(PanelProductoLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(btnLimpiar)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        PanelGeneral.addTab("Prod", PanelProducto);

        TablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "no. Cleinte", "Fecha de Venta", "IVA", "Descuento", "Subtotal", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(TablaVenta);
        if (TablaVenta.getColumnModel().getColumnCount() > 0) {
            TablaVenta.getColumnModel().getColumn(0).setPreferredWidth(20);
            TablaVenta.getColumnModel().getColumn(1).setPreferredWidth(60);
            TablaVenta.getColumnModel().getColumn(3).setPreferredWidth(20);
            TablaVenta.getColumnModel().getColumn(4).setPreferredWidth(10);
            TablaVenta.getColumnModel().getColumn(5).setPreferredWidth(20);
            TablaVenta.getColumnModel().getColumn(7).setPreferredWidth(60);
        }

        BtnVerTabla.setText("Ver tabla");
        BtnVerTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVerTablaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelVentaLayout = new javax.swing.GroupLayout(PanelVenta);
        PanelVenta.setLayout(PanelVentaLayout);
        PanelVentaLayout.setHorizontalGroup(
            PanelVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelVentaLayout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addGroup(PanelVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnVerTabla)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1056, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );
        PanelVentaLayout.setVerticalGroup(
            PanelVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVentaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtnVerTabla)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        PanelGeneral.addTab("Vent", PanelVenta);

        Tabla_hist_emp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "ID puesto", "RFC", "Fecha_Inicio", "Fecha_Fin"
            }
        ));
        jScrollPane9.setViewportView(Tabla_hist_emp);

        Tabla_hist_pro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Fecha_Compra", "Precio"
            }
        ));
        jScrollPane10.setViewportView(Tabla_hist_pro);

        Hist_emp.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Hist_emp.setText("Historial Empleado");

        Hist_product.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        Hist_product.setText("Historial Producto");

        btnActHs.setText("Actualizar");
        btnActHs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActHsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelHistorialLayout = new javax.swing.GroupLayout(PanelHistorial);
        PanelHistorial.setLayout(PanelHistorialLayout);
        PanelHistorialLayout.setHorizontalGroup(
            PanelHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHistorialLayout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(Hist_emp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Hist_product)
                .addGap(237, 237, 237))
            .addGroup(PanelHistorialLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelHistorialLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActHs)
                .addGap(547, 547, 547))
        );
        PanelHistorialLayout.setVerticalGroup(
            PanelHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHistorialLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(PanelHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Hist_emp)
                    .addComponent(Hist_product))
                .addGap(18, 18, 18)
                .addGroup(PanelHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane9)
                    .addComponent(jScrollPane10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(btnActHs))
        );

        PanelGeneral.addTab("Hist", PanelHistorial);

        TablaFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RFC", "Emisor", "Nombre", "Fecha", "id_cliente", "id_pedido"
            }
        ));
        TablaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaFacturaMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TablaFactura);

        BtnNuevoFct.setText("Nuevo");
        BtnNuevoFct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoFctActionPerformed(evt);
            }
        });

        BtnEliminarFct.setText("Eliminar");
        BtnEliminarFct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarFctActionPerformed(evt);
            }
        });

        BtnActualizarFct.setText("Actualizar");
        BtnActualizarFct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarFctActionPerformed(evt);
            }
        });

        RFCFct.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        RFCFct.setText("RFC");

        NombreFct.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        NombreFct.setText("Nombre");

        EmisorFct.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        EmisorFct.setText("Emisor");

        TFIDFct.setEditable(false);
        TFIDFct.setEnabled(false);
        TFIDFct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFIDFctActionPerformed(evt);
            }
        });

        jButton33.setText("Comprobante");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        txtIdFactura.setEditable(false);
        txtIdFactura.setEnabled(false);

        EmisorFct1.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        EmisorFct1.setText("No. pedido");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RFCFct, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(NombreFct, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .addComponent(EmisorFct, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EmisorFct1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(TFIDFct, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                .addComponent(TxtFRFCFct)
                                .addComponent(TxtFNombreFct))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtId_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(JComboEmisores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BtnNuevoFct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnActualizarFct, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnEliminarFct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtIdFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 879, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(TFIDFct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RFCFct)
                    .addComponent(TxtFRFCFct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NombreFct)
                    .addComponent(TxtFNombreFct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmisorFct)
                    .addComponent(JComboEmisores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EmisorFct1)
                    .addComponent(txtId_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtIdFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnNuevoFct)
                    .addComponent(BtnEliminarFct))
                .addGap(26, 26, 26)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnActualizarFct)
                    .addComponent(jButton33))
                .addGap(111, 111, 111))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        PanelGeneral.addTab("Fact", jPanel11);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellido Paterno", "Apellido Materno", "Telefono", "Dirección", "RFC", "CURP", "Salario", "ID_Restaurante", "Fecha de contratación", "Horario", "Puesto"
            }
        ));
        jTable7.setRowHeight(50);
        jScrollPane7.setViewportView(jTable7);
        if (jTable7.getColumnModel().getColumnCount() > 0) {
            jTable7.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable7.getColumnModel().getColumn(1).setPreferredWidth(160);
            jTable7.getColumnModel().getColumn(2).setPreferredWidth(160);
            jTable7.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable7.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTable7.getColumnModel().getColumn(8).setPreferredWidth(150);
            jTable7.getColumnModel().getColumn(9).setPreferredWidth(200);
        }

        jLabel29.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel29.setText("Nombre");

        jLabel30.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel30.setText("Apellido Paterno");

        jLabel31.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel31.setText("Apellido Materno");

        jLabel32.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel32.setText("Teléfono");

        jLabel33.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel33.setText("Dirección");

        jLabel34.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel34.setText("RFC");

        jLabel35.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel35.setText("CURP");

        jLabel36.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel36.setText("Salario");

        jLabel41.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel41.setText("Horario");

        jLabel42.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel42.setText("Puesto");

        txtApellidoPEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoPEmpActionPerformed(evt);
            }
        });

        jButton29.setText("Nuevo");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton30.setText("Eliminar");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        JcomboPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige", "Chef Ejecutivo", "Sous Chef", "Chef de Partie", "Cocinero", "Ayudante de Cocina", "Lavaplatos", "Mesero", "Hostess", "Bartender", "Barback", "Sommelier", "Gerente de Restaurante", "Supervisor de Turno", "Cajero", "Encargado de Compras", "Repostero", "Delivery", "Asistente de Limpieza", "Encargado de Reservaciones", "Encargado de Eventos" }));
        JcomboPuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcomboPuestoActionPerformed(evt);
            }
        });

        jButton40.setText("Actualizar");

        jButton41.setText("Limpiar");

        JcomboDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" }));

        JcomboJornada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7:00:00 - 15:00:00", "15:00:00 - 23:00:00", "8:00:00 - 13:00:00", "13:00:00 - 18:00:00" }));

        BtnAgregar.setText("Agregar");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(JcomboDia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JcomboJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(JcomboPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSalarioEmp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCurpEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtRFCEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtApellidoPEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNombreEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnAgregar)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTelEmp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtApellidoMEmp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 876, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtNombreEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellidoPEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtApellidoMEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTelEmp)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(BtnAgregar))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtRFCEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(txtCurpEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(txtSalarioEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(JcomboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JcomboJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(JcomboPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton30)
                            .addComponent(jButton29))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton40)
                            .addComponent(jButton41))))
                .addGap(23, 23, 23))
        );

        PanelGeneral.addTab("Emp", jPanel6);

        TablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Teléfono", "Dirección", "Correo"
            }
        ));
        jScrollPane1.setViewportView(TablaClientes);

        NombreClnt.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        NombreClnt.setText("Nombre");

        ApellidoMaternoClnt.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        ApellidoMaternoClnt.setText("Apellido Materno");

        ApellidoPaternoClnt.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        ApellidoPaternoClnt.setText("Apellido Paterno");

        TelefonoClnt.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        TelefonoClnt.setText("Teléfono");

        DIreccionClnt.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DIreccionClnt.setText("Calle");

        CorreoClnt.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        CorreoClnt.setText("Correo");

        TFApPaternoClnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFApPaternoClntActionPerformed(evt);
            }
        });

        TFNombreClnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFNombreClntActionPerformed(evt);
            }
        });

        BtnGuardarClnt.setText("Guardar");
        BtnGuardarClnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarClntActionPerformed(evt);
            }
        });

        BtnEliminarClnt.setText("Eliminar");
        BtnEliminarClnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarClntActionPerformed(evt);
            }
        });

        BtnActualizarClnt.setText("Actualizar");
        BtnActualizarClnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarClntActionPerformed(evt);
            }
        });

        BtnConsultarClnt.setText("Consultar");

        DIreccionClnt1.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DIreccionClnt1.setText("Pais");

        DIreccionClnt2.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DIreccionClnt2.setText("Codigo Postal");

        DIreccionClnt3.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        DIreccionClnt3.setText("Colonia ");

        javax.swing.GroupLayout PanelClienteLayout = new javax.swing.GroupLayout(PanelCliente);
        PanelCliente.setLayout(PanelClienteLayout);
        PanelClienteLayout.setHorizontalGroup(
            PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelClienteLayout.createSequentialGroup()
                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelClienteLayout.createSequentialGroup()
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelClienteLayout.createSequentialGroup()
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ApellidoMaternoClnt)
                                    .addComponent(ApellidoPaternoClnt))
                                .addGap(18, 18, 18)
                                .addComponent(TFApPaternoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelClienteLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelClienteLayout.createSequentialGroup()
                                        .addComponent(NombreClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TFNombreClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelClienteLayout.createSequentialGroup()
                                        .addGap(125, 125, 125)
                                        .addComponent(TFApMaternoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(DIreccionClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PanelClienteLayout.createSequentialGroup()
                                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TelefonoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(CorreoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TFCorreoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TFTelefonoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(DIreccionClnt2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DIreccionClnt1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DIreccionClnt3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelClienteLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TFDireccionClnt3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelClienteLayout.createSequentialGroup()
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BtnActualizarClnt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BtnGuardarClnt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(45, 45, 45)
                                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BtnConsultarClnt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BtnEliminarClnt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(TFDireccionClnt2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TFDireccionClnt1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TFDireccionClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        PanelClienteLayout.setVerticalGroup(
            PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelClienteLayout.createSequentialGroup()
                .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelClienteLayout.createSequentialGroup()
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NombreClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TFNombreClnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ApellidoPaternoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TFApPaternoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TFApMaternoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ApellidoMaternoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TelefonoClnt)
                            .addComponent(TFTelefonoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TFCorreoClnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorreoClnt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DIreccionClnt)
                            .addComponent(TFDireccionClnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DIreccionClnt3)
                            .addComponent(TFDireccionClnt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DIreccionClnt1)
                            .addComponent(TFDireccionClnt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DIreccionClnt2)
                            .addComponent(TFDireccionClnt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnGuardarClnt)
                            .addComponent(BtnEliminarClnt))
                        .addGap(18, 18, 18)
                        .addGroup(PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnConsultarClnt)
                            .addComponent(BtnActualizarClnt)))
                    .addGroup(PanelClienteLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        PanelGeneral.addTab("Clien", PanelCliente);

        getContentPane().add(PanelGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 1190, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void VentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VentasActionPerformed

    private void ClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClientesActionPerformed

    private void ProvedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProvedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProvedorActionPerformed

    private void FacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FacturaActionPerformed

    private void EmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmpleadoActionPerformed

    private void HistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistorialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HistorialActionPerformed

    private void NuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevaVentaActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        try {
            // Extraer datos de los campos
            String nombre = txtNombreProducto.getText();
            BigDecimal precio = new BigDecimal(txtPrecioProd.getText());
            int stock = (int) jSpinnStock.getValue();
            String descripcion = AreaDesc.getText();
            String categoriaNombre = (String) JComboCategorias.getSelectedItem();
            String proveedorNombre = (String) JcomboProveedor.getSelectedItem();

            // Llamar al controlador para insertar el producto
            Connection conn = conexion;
            ProductoControlador control = new ProductoControlador(conn);
            boolean exito = control.insertarProducto(nombre, precio, stock, descripcion, categoriaNombre, proveedorNombre);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Producto insertado correctamente");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar producto");
                cargarTabla();
            }      
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void txtPrecioProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioProdActionPerformed

    private void BtnNuevoPddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoPddActionPerformed
        ClientesFrame cliente = new ClientesFrame();
        cliente.setVisible(true);
                
    }//GEN-LAST:event_BtnNuevoPddActionPerformed

    private void BtnActualizarPrvdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarPrvdActionPerformed
        try {
            // Extraer datos de los campos
            int fila = TablaProvedor.getSelectedRow();
            int idProveedor = Integer.parseInt(TablaProvedor.getValueAt(fila, 0).toString());
            String nombre = txtNombreProv.getText();
            String direccion = txtCalleProvedor.getText()+","+txtColoniaProveedor.getText()+
                    ","+txtPaisProv.getText()+","+txtCodigoPProv.getText();
            String telefono = txtTelefonoProv.getText();

            // Llamar al controlador para insertar el proveedor
            Connection conn = conexion;
            ProveedorDAO control = new ProveedorDAO(conn);
            
            Proveedor prvd = new Proveedor();
            prvd.setNombre(nombre);
            prvd.setDireccion(direccion);
            prvd.setTelefono(telefono);
            boolean exito = control.actualizarProveedor(prvd,idProveedor);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Proveedor Actualizado correctamente");
                cargarTablaProveedor();
            } else {
                JOptionPane.showMessageDialog(this, "Error al Actualizar proveedor");
                cargarTablaProveedor();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_BtnActualizarPrvdActionPerformed

    private void BtnGuardarPrvdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarPrvdActionPerformed
                                  
        try {
            // Extraer datos de los campos
            String nombre = txtNombreProv.getText();
            String direccion = txtCalleProvedor.getText()+","+txtColoniaProveedor.getText()+
                    ","+txtPaisProv.getText()+","+txtCodigoPProv.getText();
            String telefono = txtTelefonoProv.getText();

            // Llamar al controlador para insertar el proveedor
            Connection conn = conexion;
            ProveedorDAO control = new ProveedorDAO(conn);
            
            Proveedor prvd = new Proveedor();
            prvd.setNombre(nombre);
            prvd.setDireccion(direccion);
            prvd.setTelefono(telefono);
            
            boolean exito = control.crearProveedor(prvd);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Proveedor insertado correctamente");
                cargarTablaProveedor();
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar proveedor");
                cargarTablaProveedor();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }        
    }//GEN-LAST:event_BtnGuardarPrvdActionPerformed

    private void BtnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCerrarSesionActionPerformed
        // TODO add your handling code here:
        Conexion cerrar = new Conexion();
        cerrar.cerrarConexion();
        this.setVisible(false);
        Login log = new Login();
        log.setVisible(true);
    }//GEN-LAST:event_BtnCerrarSesionActionPerformed

    private void TFApPaternoClntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFApPaternoClntActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFApPaternoClntActionPerformed

    private void TFNombreClntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFNombreClntActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFNombreClntActionPerformed

    private void BtnGuardarClntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarClntActionPerformed
      try {
        // Extraer datos de los campos de la vista
        String nombre = TFNombreClnt.getText();
        String apellidoPaterno = TFApPaternoClnt.getText();
        String apellidoMaterno = TFApMaternoClnt.getText();
        String telefono = TFTelefonoClnt.getText();
        String correo = TFCorreoClnt.getText();
        
        // Obtener la dirección
        String calle = TFDireccionClnt.getText();
        String colonia = TFDireccionClnt1.getText();
        String pais = TFDireccionClnt3.getText();
        String cp = TFDireccionClnt2.getText();

        // Crear el objeto Direccion
        Direccion direccion = new Direccion(calle, colonia, pais, cp);

        // Crear el objeto Cliente con los datos extraídos
        Cliente cliente = new Cliente(0, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion, correo);

        // Crear una instancia de ClienteDAO
        ClienteDAO clienteDAO = new ClienteDAO(conexion); // Asegúrate de que 'conexion' es tu conexión a la base de datos
        
        // Llamar al método para guardar el cliente
        Cliente clienteGuardado = clienteDAO.crearCliente(cliente);

        // Verificar si el cliente fue insertado correctamente
        if (clienteGuardado != null && clienteGuardado.getIdPersona() > 0) {
            JOptionPane.showMessageDialog(this, "Cliente insertado correctamente");
          //  cargarTablaClientes(); // Si tienes una tabla para mostrar los clientes
        } else {
            JOptionPane.showMessageDialog(this, "Error al insertar el cliente");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
}
    }//GEN-LAST:event_BtnGuardarClntActionPerformed

    private void BtnEliminarClntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarClntActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEliminarClntActionPerformed

    private void BtnNuevoFctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoFctActionPerformed
         try {
        // Obtener los datos de los campos de texto
        String rfc = TxtFRFCFct.getText();
        String nombre = TxtFNombreFct.getText();
        String emisor = String.valueOf(JComboEmisores.getSelectedItem());
        int idCliente = Integer.parseInt(TFIDFct.getText()); // Asegúrate de tener un campo de texto para el ID cliente

        // Crear un objeto de tipo Factura
       
        Factura nuevaFactura = new Factura(rfc, null, idCliente, 0, nombre);  // El ID y la fecha serán manejados por la base de datos
        nuevaFactura.setRfcCliente(rfc);
        nuevaFactura.setNombre_cliente(nombre);
        nuevaFactura.setFechaExpedicion(new Date(System.currentTimeMillis())); // Fecha actual

        // Crear el objeto FacturaDAO y llamar al método para guardar la factura
        FacturaDAO facturaDAO = new FacturaDAO(conexion);
        Factura facturaGuardada = facturaDAO.crearFactura(nuevaFactura);

        if (facturaGuardada != null) {
            JOptionPane.showMessageDialog(this, "Factura creada con éxito!");
            cargarTablaFactura();  // Llamar al método para actualizar la tabla de facturas
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear la factura.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al guardar la factura: " + e.getMessage());}


    }//GEN-LAST:event_BtnNuevoFctActionPerformed

    private void BtnEliminarFctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarFctActionPerformed
       try {
        // Verificar que se haya seleccionado una fila en la tabla
        int fila = TablaFactura.getSelectedRow();
        if (fila != -1) {
            // Obtener el id de la factura desde la tabla
            int idFactura = Integer.parseInt(TablaFactura.getValueAt(fila, 0).toString());

            // Crear instancia del DAO para eliminar la factura
            FacturaDAO facturaDAO = new FacturaDAO(conexion);
            boolean exito = facturaDAO.eliminarFactura(idFactura);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Factura eliminada correctamente.");
                cargarTablaFactura();  // Recargar la tabla de facturas
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la factura.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una factura para eliminar.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
}
    }//GEN-LAST:event_BtnEliminarFctActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton30ActionPerformed

    private void txtApellidoPEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoPEmpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoPEmpActionPerformed

    private void JcomboPuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcomboPuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JcomboPuestoActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton33ActionPerformed

    private void BtnPagarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPagarVentaActionPerformed
        // TODO add your handling code here:
        int[] idsProductos = carrito.getIdsProductos().stream().mapToInt(Integer::intValue).toArray();
        int[] cantidades = carrito.getCantidades().stream().mapToInt(Integer::intValue).toArray();
        double iva = 0.16;
        double descuento = (JcomboDescuento.getSelectedItem().toString() == "0%") ? 0.0 : (JcomboDescuento.getSelectedItem().toString() == "5%") ? 0.05 : 
                (JcomboDescuento.getSelectedItem().toString() == "10%") ? 0.10 : (JcomboDescuento.getSelectedItem().toString() == "15%") ? 0.15 : (JcomboDescuento.getSelectedItem().toString() == "20%") ? 0.20 : 0.30;
        String tipo_venta = "En restaurante";
        String metodoPago = jComboMetodoDePago.getSelectedItem().toString();
        VentaDAO nuevaVenta = new VentaDAO(conexion);
        try {
            boolean exito =  nuevaVenta.crearVenta(iva, descuento, tipo_venta, metodoPago, idsProductos, cantidades);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Venta realizada correctamente");
                cargarTabla();
                
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar venta");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_BtnPagarVentaActionPerformed

    private void BtnEliminarNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEliminarNVActionPerformed

    private void BtnActualizarClntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarClntActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnActualizarClntActionPerformed

    private void JcomboDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcomboDescuentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JcomboDescuentoActionPerformed

    private void JcomboProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcomboProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JcomboProductosActionPerformed

    private void JcomboCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcomboCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JcomboCantidadActionPerformed

    private void jComboMetodoDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMetodoDePagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboMetodoDePagoActionPerformed

    private void JComboCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JComboCategoriasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JComboCategoriasActionPerformed

    private void BtnActualizarPrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarPrdActionPerformed
        try{
            // Extraer datos de los campos
            int fila = TablaProducto.getSelectedRow();
            int idProducto = Integer.parseInt(TablaProducto.getValueAt(fila, 0).toString());
            String nombre = txtNombreProducto.getText();
            BigDecimal precio = new BigDecimal(txtPrecioProd.getText());
            int stock = (int) jSpinnStock.getValue();
            String descripcion = AreaDesc.getText();
            String categoriaNombre = (String) JComboCategorias.getSelectedItem();
            String proveedorNombre = (String) JcomboProveedor.getSelectedItem();
            ProductoControlador control = new ProductoControlador(conexion);
            boolean exito = control.actualizarProducto(idProducto,nombre,precio,stock,descripcion,categoriaNombre,proveedorNombre);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Producto Actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al Acualizar producto");
            }
            cargarTabla();
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }                        
    }//GEN-LAST:event_BtnActualizarPrdActionPerformed

    private void TablaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProductoMouseClicked
        int fila = TablaProducto.getSelectedRow();
        int id = Integer.parseInt(TablaProducto.getValueAt(fila, 0).toString());
        Productos consulta = new ProductosDAO(conexion).obtenerProductosPorId(id);
        Proveedor proveedor = new Proveedor();
        Categoria cats = new Categoria();
        CategoriaDAO cat = new CategoriaDAO(conexion);
        ProveedorDAO prov = new ProveedorDAO(conexion);
        txtNombreProducto.setText(consulta.getNombre());
        txtPrecioProd.setText(String.valueOf(consulta.getPrecio()));
        jSpinnStock.setValue(consulta.getStock());
        AreaDesc.setText(consulta.getDescripcion());
        JComboCategorias.setSelectedItem(cat.obtenerCategoriaPorId(consulta.getIdCat(),cats));
        JcomboProveedor.setSelectedItem(prov.obtenerProveedorPorId(consulta.getIdProveedor(),proveedor));
        txtIDproducto.setText(String.valueOf(id));
    }//GEN-LAST:event_TablaProductoMouseClicked

    private void BtnEliminarPrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarPrdActionPerformed
        try {
            int idProducto = Integer.parseInt(txtIDproducto.getText());
            ProductosDAO eliminarProducto = new ProductosDAO(conexion);
            eliminarProducto.eliminarProductos(idProducto);
            JOptionPane.showMessageDialog(null, "Eliminado con éxito");
            cargarTabla();
            limpiarTablaProductos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_BtnEliminarPrdActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarTablaProductos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void BtnEliminarPrvdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarPrvdActionPerformed
        try {
            int idProveedor = Integer.parseInt(txtIdProveedor.getText());
            ProveedorDAO eliminacion = new ProveedorDAO(conexion);
            boolean exito = eliminacion.eliminarProveedor(idProveedor);
            if (exito){
                JOptionPane.showMessageDialog(null, "Proveedor Eliminado con éxito");
                cargarTablaProveedor();
                limpiarProveedor();
            }else{
                JOptionPane.showMessageDialog(this, "Error al eliminar Proveedor");
            }
            cargarTablaProveedor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_BtnEliminarPrvdActionPerformed

    private void TablaProvedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProvedorMouseClicked
        int fila = TablaProvedor.getSelectedRow();
        int id = Integer.parseInt(TablaProvedor.getValueAt(fila, 0).toString());
        Proveedor consulta = new ProveedorDAO(conexion).obtenerProveedorPorId(id);
        txtNombreProv.setText(consulta.getNombre());
        txtCalleProvedor.setText(consulta.getDireccion().getCalle());
        txtColoniaProveedor.setText(consulta.getDireccion().getColonia());
        txtPaisProv.setText(consulta.getDireccion().getPais());
        txtCodigoPProv.setText(consulta.getDireccion().getCp());
        txtTelefonoProv.setText(consulta.getTelefono());
        txtIdProveedor.setText(String.valueOf(id));
    }//GEN-LAST:event_TablaProvedorMouseClicked

    private void BtnLimpiarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarProveedorActionPerformed
        txtNombreProv.setText("");
        txtCalleProvedor.setText("");
        txtColoniaProveedor.setText("");
        txtPaisProv.setText("");
        txtCodigoPProv.setText("");
        txtTelefonoProv.setText("");
    }//GEN-LAST:event_BtnLimpiarProveedorActionPerformed

    private void txtPrecioUVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioUVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioUVentaActionPerformed

    private void btnAñadirProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirProductosActionPerformed

        PedidoDAO productos = new PedidoDAO(conexion);
        ProductosDAO idProd = new ProductosDAO(conexion);
        Productos nombreProducto = new Productos();
        String producto = JcomboProductos.getSelectedItem().toString();
        nombreProducto.setNombre(producto);
        int cantidad = Integer.parseInt(JcomboCantidad.getSelectedItem().toString());
        int id_producto = idProd.obtenerIdProductoNombre(nombreProducto);
        
        
        double subtotalDesc = productos.calcularPrecioTotal(producto, cantidad);
        carrito.setSubTotal(productos.calcularPrecioTotal(producto, cantidad));
        txtPrecioUVenta.setText(String.valueOf(subtotalDesc));
        
        
        double total = carrito.calcularTotal(subtotalDesc);
        txtTotalVenta.setText(String.valueOf(total));
        this.carrito.agregarProducto(id_producto, cantidad);
        this.carrito.mostrarCarrito();
        // Crear una ventana principal para probar el mensaje temporal
        JFrame frame = new JFrame("Aviso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 100);
        frame.setLayout(new FlowLayout());
        mostrarMensaje(frame, "Producto Añadido", 1500);
    }//GEN-LAST:event_btnAñadirProductosActionPerformed

    private void btnConsultarCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarCarritoActionPerformed
        CarritoFrame carrito = new CarritoFrame();
        carrito.setVisible(true);
    }//GEN-LAST:event_btnConsultarCarritoActionPerformed

    private void txtTotalVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalVentaActionPerformed

    private void JcomboProductosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JcomboProductosItemStateChanged
        /*PedidoDAO productos = new PedidoDAO(conexion);
        ProductosDAO idProd = new ProductosDAO(conexion);
        Productos nombreProducto = new Productos();
        String producto = JcomboProductos.getSelectedItem().toString();
        nombreProducto.setNombre(producto);
        int cantidad = Integer.parseInt(JcomboCantidad.getSelectedItem().toString());
        int id_producto = idProd.obtenerIdProductoNombre(nombreProducto);
        double descuento = (JcomboDescuento.getSelectedItem().toString() == "0%") ? 0.0 : (JcomboDescuento.getSelectedItem().toString() == "5%") ? 0.05 : 
                (JcomboDescuento.getSelectedItem().toString() == "10%") ? 0.10 : (JcomboDescuento.getSelectedItem().toString() == "15%") ? 0.15 : (JcomboDescuento.getSelectedItem().toString() == "20%") ? 0.20 : 0.30;
        
        double subtotal = productos.calcularPrecioTotal(producto, cantidad,descuento);
        txtPrecioUVenta.setText(String.valueOf(subtotal));*/
    }//GEN-LAST:event_JcomboProductosItemStateChanged

    private void BtnLimpiarNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarNVActionPerformed
        JcomboDescuento.setSelectedIndex(0);
        jSpinnStock.setValue(1);
        txtPrecioProd.setText("");
        txtTotalVenta.setText("");
        carrito.limpiarCarrito();
    }//GEN-LAST:event_BtnLimpiarNVActionPerformed

    private void JcomboCantidadDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcomboCantidadDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JcomboCantidadDocActionPerformed

    private void btnAñadirProductos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirProductos1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAñadirProductos1ActionPerformed

    private void btnConsultarCarrito1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarCarrito1ActionPerformed
        CarritoFrame carrito = new CarritoFrame();
        carrito.setVisible(true);
    }//GEN-LAST:event_btnConsultarCarrito1ActionPerformed

    private void JcomboDescuento1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcomboDescuento1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JcomboDescuento1ActionPerformed

    private void txtTotalVenta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalVenta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalVenta1ActionPerformed

    private void BtnConsultarPddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConsultarPddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnConsultarPddActionPerformed

    private void btnActHsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActHsActionPerformed
       cargarTablaHspProductos();
       cargarTablaHsEmp();
       // Crear una ventana principal para probar el mensaje temporal
        JFrame frame = new JFrame("Aviso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 100);
        frame.setLayout(new FlowLayout());
        mostrarMensaje(frame, "Tablas actualizadas...", 1200);
    }//GEN-LAST:event_btnActHsActionPerformed

    private void txtCodigoPProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoPProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoPProvActionPerformed

    private void BtnVerTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVerTablaActionPerformed
        // Crear una ventana principal para probar el mensaje temporal
        JFrame frame = new JFrame("Aviso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 100);
        frame.setLayout(new FlowLayout());
        mostrarMensaje(frame, "Cargando tabla...", 15000);
        cargarTablaVentasTotales();
    }//GEN-LAST:event_BtnVerTablaActionPerformed

    private void BtnActualizarFctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarFctActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnActualizarFctActionPerformed

    private void TablaFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaFacturaMouseClicked
        int fila = TablaProducto.getSelectedRow();
        int id = Integer.parseInt(TablaProducto.getValueAt(fila, 0).toString());
        txtIdFactura.setText(String.valueOf(id));
        //Factura consulta = new 
    }//GEN-LAST:event_TablaFacturaMouseClicked

    private void TFIDFctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFIDFctActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFIDFctActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ApellidoMaternoClnt;
    private javax.swing.JLabel ApellidoPaternoClnt;
    private java.awt.TextArea AreaDesc;
    private javax.swing.JButton BtnActualizarClnt;
    private javax.swing.JButton BtnActualizarFct;
    private javax.swing.JButton BtnActualizarPdd;
    private javax.swing.JButton BtnActualizarPrd;
    private javax.swing.JButton BtnActualizarPrvd;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JLabel BtnCategoriaPrd;
    private javax.swing.JButton BtnCerrarSesion;
    private javax.swing.JButton BtnConsultarClnt;
    private javax.swing.JButton BtnConsultarPdd;
    private javax.swing.JButton BtnConsultarPrd;
    private javax.swing.JLabel BtnDescripcionPrd;
    private javax.swing.JButton BtnEliminarClnt;
    private javax.swing.JButton BtnEliminarFct;
    private javax.swing.JButton BtnEliminarNV;
    private javax.swing.JButton BtnEliminarPdd;
    private javax.swing.JButton BtnEliminarPrd;
    private javax.swing.JButton BtnEliminarPrvd;
    private javax.swing.JButton BtnGuardarClnt;
    private javax.swing.JButton BtnGuardarPrvd;
    private javax.swing.JButton BtnLimpiarNV;
    private javax.swing.JButton BtnLimpiarProveedor;
    private javax.swing.JButton BtnNuevoFct;
    private javax.swing.JButton BtnNuevoPdd;
    private javax.swing.JButton BtnPagarVenta;
    private javax.swing.JButton BtnVerTabla;
    private javax.swing.JComboBox<String> CBMetododePagoPdd;
    private javax.swing.JComboBox<String> CBProductosPdd;
    private javax.swing.JLabel CantidadNV;
    private javax.swing.JLabel CantidadPdd;
    private javax.swing.JButton Clientes;
    private javax.swing.JLabel CorreoClnt;
    private javax.swing.JLabel DIreccionClnt;
    private javax.swing.JLabel DIreccionClnt1;
    private javax.swing.JLabel DIreccionClnt2;
    private javax.swing.JLabel DIreccionClnt3;
    private javax.swing.JLabel DescuentoNV;
    private javax.swing.JLabel DescuentoNV1;
    private javax.swing.JLabel DireccionPrvd;
    private javax.swing.JLabel DireccionPrvd1;
    private javax.swing.JLabel DireccionPrvd2;
    private javax.swing.JLabel DireccionPrvd3;
    private javax.swing.JLabel EmisorFct;
    private javax.swing.JLabel EmisorFct1;
    private javax.swing.JButton Empleado;
    private javax.swing.JButton Factura;
    private javax.swing.JLabel Hist_emp;
    private javax.swing.JLabel Hist_product;
    private javax.swing.JButton Historial;
    private javax.swing.JLabel ImagenPrincipal;
    private javax.swing.JComboBox<String> JComboCategorias;
    private javax.swing.JComboBox<String> JComboEmisores;
    private javax.swing.JComboBox<String> JcomboCantidad;
    private javax.swing.JComboBox<String> JcomboCantidadDoc;
    private javax.swing.JComboBox<String> JcomboDescuento;
    private javax.swing.JComboBox<String> JcomboDescuento1;
    private javax.swing.JComboBox<String> JcomboDia;
    private javax.swing.JComboBox<String> JcomboJornada;
    private javax.swing.JComboBox<String> JcomboProductos;
    private javax.swing.JComboBox<String> JcomboProveedor;
    private javax.swing.JComboBox<String> JcomboPuesto;
    private javax.swing.JLabel MetodoDePago;
    private javax.swing.JLabel MetodoDePago1;
    private javax.swing.JLabel MetodoDePago2;
    private javax.swing.JLabel MetodoDePago3;
    private javax.swing.JLabel MetododePagoPdd;
    private javax.swing.JLabel NombreClnt;
    private javax.swing.JLabel NombreFct;
    private javax.swing.JLabel NombrePrd;
    private javax.swing.JLabel NombrePrvd;
    private javax.swing.JButton NuevaVenta;
    private javax.swing.JPanel PanelCliente;
    private javax.swing.JTabbedPane PanelGeneral;
    private javax.swing.JPanel PanelHistorial;
    private javax.swing.JPanel PanelNuevaVenta;
    private javax.swing.JPanel PanelPedido;
    private javax.swing.JPanel PanelProducto;
    private javax.swing.JPanel PanelProvedor;
    private javax.swing.JPanel PanelVenta;
    private javax.swing.JButton Pedidos;
    private javax.swing.JLabel PrecioPdd;
    private javax.swing.JLabel PrecioPrd;
    private javax.swing.JLabel Producto;
    private javax.swing.JButton Productos;
    private javax.swing.JLabel ProductosPdd;
    private javax.swing.JButton Provedor;
    private javax.swing.JLabel ProvedorPrd;
    private javax.swing.JLabel RFCFct;
    private javax.swing.JLabel StockPrd;
    private javax.swing.JTextField TFApMaternoClnt;
    private javax.swing.JTextField TFApPaternoClnt;
    private javax.swing.JTextField TFCorreoClnt;
    private javax.swing.JTextField TFDireccionClnt;
    private javax.swing.JTextField TFDireccionClnt1;
    private javax.swing.JTextField TFDireccionClnt2;
    private javax.swing.JTextField TFDireccionClnt3;
    private javax.swing.JTextField TFIDFct;
    private javax.swing.JTextField TFNombreClnt;
    private javax.swing.JTextField TFPrecioPdd;
    private javax.swing.JTextField TFTelefonoClnt;
    private javax.swing.JTable TablaClientes;
    private javax.swing.JTable TablaFactura;
    private javax.swing.JTable TablaNV;
    private javax.swing.JTable TablaPedido;
    private javax.swing.JTable TablaProducto;
    private javax.swing.JTable TablaProvedor;
    private javax.swing.JTable TablaVenta;
    private javax.swing.JTable Tabla_hist_emp;
    private javax.swing.JTable Tabla_hist_pro;
    private javax.swing.JLabel TelefonoClnt;
    private javax.swing.JLabel TelefonoPrvd;
    private javax.swing.JTextField TxtFNombreFct;
    private javax.swing.JTextField TxtFRFCFct;
    private javax.swing.JButton Ventas;
    private javax.swing.JButton btnActHs;
    private javax.swing.JButton btnAñadirProductos;
    private javax.swing.JButton btnAñadirProductos1;
    private javax.swing.JButton btnConsultarCarrito;
    private javax.swing.JButton btnConsultarCarrito1;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JComboBox<String> jComboMetodoDePago;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSpinner jSpinnStock;
    private javax.swing.JTable jTable7;
    private javax.swing.JTextField txtApellidoMEmp;
    private javax.swing.JTextField txtApellidoPEmp;
    private javax.swing.JTextField txtCalleProvedor;
    private javax.swing.JTextField txtCodigoPProv;
    private javax.swing.JTextField txtColoniaProveedor;
    private javax.swing.JTextField txtCurpEmp;
    private javax.swing.JTextField txtIDproducto;
    private javax.swing.JTextField txtIdFactura;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtId_pedido;
    private javax.swing.JTextField txtNombreEmp;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNombreProv;
    private javax.swing.JTextField txtPaisProv;
    private javax.swing.JTextField txtPrecioProd;
    private javax.swing.JTextField txtPrecioUVenta;
    private javax.swing.JTextField txtRFCEmp;
    private javax.swing.JTextField txtSalarioEmp;
    private javax.swing.JTextField txtTelEmp;
    private javax.swing.JTextField txtTelefonoProv;
    private javax.swing.JTextField txtTotalVenta;
    private javax.swing.JTextField txtTotalVenta1;
    // End of variables declaration//GEN-END:variables

    private void cargarComboProducto(JComboBox c) {
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        c.setModel(combo);
        ListadoProductos lista_productos = new ListadoProductos();
        try{
            Connection conn = conexion;
            if (conn == null) {
                throw new SQLException("Conexión no inicializada.");
            }
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre FROM restaurante.productos order by id_producto ASC");
            while(rs.next()){
                Productos prod = new Productos();
                prod.setNombre(rs.getString(1));
                lista_productos.AgregarProductos(prod);
                combo.addElement(prod.getNombre());
            }
            System.out.println("Exito productos");
        } catch(Exception e){
            System.out.println("Error al consultar combo: "+e);
        }
    }

    private void cargarComboProveedor(JComboBox comboProveedor) {
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        comboProveedor.setModel(combo);
        ListadoProveedores proveedores = new ListadoProveedores();
        try{
            Connection conn = conexion;
            if (conn == null) {
                throw new SQLException("Conexión no inicializada.");
            }
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre FROM restaurante.proveedor ORDER BY id_proveedor ASC");
            while(rs.next()){
                Proveedor prov = new Proveedor();
                prov.setNombre(rs.getString(1));
                proveedores.AgregarProveedores(prov);
                combo.addElement(prov.getNombre());
            }
            System.out.println("Exito proveedores");
        } catch(Exception e){
            System.out.println("Error al consultar combo: "+e);
        }
    }

    private void cargarComboCategorias(JComboBox JComboCategorias) {
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        JComboCategorias.setModel(combo);
        ListadoCategorias lista_cat = new ListadoCategorias();
        try{
            Connection conn = conexion;
            if (conn == null) {
                throw new SQLException("Conexión no inicializada.");
            }
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT categorias FROM restaurante.categoria ORDER BY id_cat ASC");
            while(rs.next()){
                Categoria cat = new Categoria();
                cat.setCategorias(rs.getString(1));
                lista_cat.AgregarCategorias(cat);
                combo.addElement(cat.getCategorias());
            }
            System.out.println("Exito Categorias");
        } catch(Exception e){
            System.out.println("Error al consultar combo: "+e);
        }
    }
    
    private void cargarComboPuestos(JComboBox JComboPuestos){
        DefaultComboBoxModel combo = new DefaultComboBoxModel();
        JComboPuestos.setModel(combo);
        ListaPuesto lista_puesto = new ListaPuesto();
        try {
            Connection conn = conexion;
            if(conn == null){
                throw new SQLException("Conexión no inicializada.");
            }
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT titulo_puesto FROM restaurante.puesto ORDER BY id_puesto");
            while(rs.next()){
                Puesto puesto = new Puesto();
                puesto.setTituloPuesto(rs.getString(1));
                lista_puesto.AgregarPuestos(puesto);
                combo.addElement(puesto.getTituloPuesto());
            }
            System.out.println("Exito Puestos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void limpiarProveedor(){
        txtNombreProv.setText("");
        txtCalleProvedor.setText("");
        txtColoniaProveedor.setText("");
        txtPaisProv.setText("");
        txtCodigoPProv.setText("");
        txtTelefonoProv.setText("");
    }
}
