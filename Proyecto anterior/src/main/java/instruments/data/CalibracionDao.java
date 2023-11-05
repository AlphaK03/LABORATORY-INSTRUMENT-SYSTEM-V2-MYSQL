package instruments.data;

import instruments.logic.Calibracion;
import instruments.logic.Instrumento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CalibracionDao {
    Database db;

    public CalibracionDao() {
        db = Database.instance();
    }

    public void create(Calibracion calibracion) throws Exception {
        String sql = "insert into " +
                "Calibracion " +
                "(numero, instrumento_serie, fecha, mediciones) " +
                "values(?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, calibracion.getNumero());
        stm.setString(2, calibracion.getInstrumentoCalibrado().getSerie());
        stm.setString(3, calibracion.getFecha());
        stm.setInt(4, calibracion.getCantidadMediciones());

        db.executeUpdate(stm);
    }

    public Calibracion read(String numero) throws Exception {
        String sql = "select " +
                "* " +
                "from Calibracion c " +
                "where c.numero=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, numero);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs, "c");
        } else {
            throw new Exception("CALIBRACION NO EXISTE");
        }
    }

    public void update(Calibracion calibracion) throws Exception {
        String sql = "update " +
                "Calibracion " +
                "set instrumento_serie=?, fecha=?, mediciones=? " +
                "where numero=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, calibracion.getInstrumentoCalibrado().getSerie());
        stm.setString(2, calibracion.getFecha());
        stm.setInt(3, calibracion.getCantidadMediciones());
        stm.setInt(4, calibracion.getNumero());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("CALIBRACION NO EXISTE");
        }
    }

    public void delete(Calibracion calibracion) throws Exception {
        String sql = "delete " +
                "from Calibracion " +
                "where numero=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, calibracion.getNumero());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("CALIBRACION NO EXISTE");
        }
    }

    public List<Calibracion> search(Calibracion calibracion) throws Exception {
        List<Calibracion> resultado = new ArrayList<Calibracion>();
        String sql = "select * " +
                "from Calibracion c " +
                "where c.numero like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + calibracion.getNumero() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            resultado.add(from(rs, "c"));
        }
        return resultado;
    }

    public Calibracion from(ResultSet rs, String alias) throws Exception {
        Calibracion calibracion = new Calibracion();
        calibracion.setNumero(Integer.parseInt(rs.getString(alias + ".numero")));
        calibracion.setFecha(String.valueOf(rs.getDate(alias + ".fecha")));
        calibracion.setMediciones(rs.getInt(alias + ".mediciones"));

        // Obtener el instrumento usando la serie del instrumento
        String instrumentoSerie = rs.getString(alias + ".instrumento_serie");
        Instrumento instrumento = new InstrumentosDao().read(instrumentoSerie);
        calibracion.setInstrumentoCalibrado(instrumento);

        return calibracion;
    }
}

