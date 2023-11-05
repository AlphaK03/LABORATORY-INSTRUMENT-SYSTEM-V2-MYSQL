package instruments.data;

import instruments.logic.Instrumento;
import instruments.logic.TipoInstrumento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InstrumentosDao {

    Database db;
    public InstrumentosDao() {db = Database.instance();}

    public void create(Instrumento e) throws Exception {
        String sql = "insert into " +
                "Instrumento " +
                "(serie, tipo, descripcion, minimo, maximo, tolerancia) " +
                "values(?,?,?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getSerie());
        stm.setString(2, e.getTipoInstrumento().getCodigo());
        stm.setString(3, e.getDescripcion());
        stm.setInt(4, (int) e.getMinimo());   // Use index 4 for minimo
        stm.setInt(5, (int) e.getMaximo());   // Use index 5 for maximo
        stm.setInt(6, (int) e.getTolerancia()); // Use index 6 for tolerancia

        db.executeUpdate(stm);
    }


    public Instrumento read(String serie) throws Exception {
        String sql = "select " +
                "* " +
                "from  Instrumento t " +
                "where t.serie=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, serie);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs, "t");
        } else {
            throw new Exception("INSTRUMENTO NO EXISTE");
        }
    }

    public void update(Instrumento e) throws Exception {
        String sql = "update " +
                "Instrumento " +
                "set tipo=?, descripcion=?, minimo=?, maximo=?, tolerancia=? " +
                "where serie=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getTipoInstrumento().getCodigo());
        stm.setString(2, e.getDescripcion());
        stm.setInt(3, (int) e.getMinimo());
        stm.setInt(4, (int) e.getMaximo());
        stm.setInt(5, (int) e.getTolerancia());
        stm.setString(6, e.getSerie());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("INSTRUMENTO NO EXISTE");
        }

    }

    public void delete(Instrumento e) throws Exception {
        String sql = "delete " +
                "from Instrumento " +
                "where serie=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getSerie());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("INSTRUMENTO NO EXISTE");
        }
    }

    public List<Instrumento> search(Instrumento e) throws Exception {
        List<Instrumento> resultado = new ArrayList<Instrumento>();
        String sql = "select * " +
                "from " +
                "Instrumento t " +
                "where t.tipo like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getTipoInstrumento() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            resultado.add(from(rs, "t"));
        }
        return resultado;
    }

    public Instrumento from(ResultSet rs, String alias) throws Exception {
        Instrumento e = new Instrumento();
        e.setSerie(rs.getString(alias + ".Serie"));
        String tipoValue = rs.getString(alias + ".Tipo");

        // Convert the database value to uppercase before using valueOf
        TipoInstrumento tipo = new TipoInstrumentoDao().read(tipoValue.toUpperCase());

        e.setTipoInstrumento(tipo);
        e.setDescripcion(rs.getString(alias + ".descripcion"));
        e.setMinimo(rs.getInt(alias + ".Minimo"));
        e.setMaximo(rs.getInt(alias + ".Maximo"));
        e.setTolerancia(rs.getInt(alias + ".Tolerancia"));

        return e;
    }

}
