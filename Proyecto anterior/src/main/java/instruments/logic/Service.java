package instruments.logic;

import instruments.data.TipoInstrumentoDao;
import instruments.data.InstrumentosDao;
import instruments.data.CalibracionDao;
import java.util.List;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    private TipoInstrumentoDao tipoInstrumentoDao;
    private InstrumentosDao instrumentosDao;
    private CalibracionDao calibracionDao;

    public Service() {
        try {
            tipoInstrumentoDao = new TipoInstrumentoDao();
            instrumentosDao = new InstrumentosDao();
        } catch (Exception e) {
            // Maneja la excepción adecuadamente
        }
    }

    // ------------ TIPOS DE INTRUMENTO -------------

    public void create(TipoInstrumento e) throws Exception {
        tipoInstrumentoDao.create(e);
    }

    public TipoInstrumento read(TipoInstrumento e) throws Exception {
        return tipoInstrumentoDao.read(e.getCodigo());
    }

    public void update(TipoInstrumento e) throws Exception {
        tipoInstrumentoDao.update(e);
    }

    public void delete(TipoInstrumento e) throws Exception {
        tipoInstrumentoDao.delete(e);
    }

    public List<TipoInstrumento> search(TipoInstrumento e) throws Exception {
        return tipoInstrumentoDao.search(e);
    }

    // ------------ INSTRUMENTOS -------------

    public void create(Instrumento e) throws Exception {
        instrumentosDao.create(e);
    }

    public Instrumento read(Instrumento e) throws Exception {
        return instrumentosDao.read(e.getSerie());
    }

    public void update(Instrumento e) throws Exception {
        instrumentosDao.update(e);
    }

    public void delete(Instrumento e) throws Exception {
        instrumentosDao.delete(e);
    }

    public List<Instrumento> search(Instrumento v) throws Exception {
        return instrumentosDao.search(v);
    }
    // ------------ CALIBRACIONES -------------

    public void create(Calibracion e) throws Exception {
        calibracionDao.create(e);
    }

    public Calibracion read(Calibracion e) throws Exception {
        return calibracionDao.read(String.valueOf(e.getNumero()));
    }

    public void update(Calibracion e) throws Exception {
        calibracionDao.update(e);
    }

    public void delete(Calibracion e) throws Exception {
        calibracionDao.delete(e);
    }

    public List<Calibracion> search(Calibracion v) throws Exception {
        return calibracionDao.search(v);
    }
}
