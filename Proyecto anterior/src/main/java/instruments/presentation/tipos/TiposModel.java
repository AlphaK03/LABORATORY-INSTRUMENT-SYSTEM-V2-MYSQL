package instruments.presentation.tipos;

import instruments.logic.Service;
import instruments.logic.TipoInstrumento;
import instruments.Application;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class TiposModel extends java.util.Observable{
    List<TipoInstrumento> list;

    TipoInstrumento current;
    TipoInstrumento filter;

    int changedProps = NONE;
    int mode;
    int props;

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }

    public void commit() {
        setChanged();
        notifyObservers(changedProps);
        notifyObservers(LIST);
        changedProps = NONE;
    }


    public TiposModel() {
    }

    public void init(){
        props=0;
        filter = new TipoInstrumento();
        List<TipoInstrumento> list = new ArrayList<TipoInstrumento>();
        this.setList(list);
        mode= Application.MODE_CREATE;
    }

    //Modificar esta función clasificando el retun segúan sea el caso solicitado
    public List<TipoInstrumento> getList() {
        return list;
    }


    public void setList(List<TipoInstrumento> list){
        this.list = list;
        changedProps +=LIST;
    }

    public TipoInstrumento getCurrent() {
        return current;
    }
    public void setCurrent(TipoInstrumento current) {
        changedProps +=CURRENT;
        this.current = current;
    }

    public void delete(TipoInstrumento tipoInstrumento) {
        list.remove(tipoInstrumento);
        setCurrent(new TipoInstrumento());
        changedProps |= LIST;
        commit();
    }

    public void enableEditing() {
        setChanged();
        notifyObservers(CURRENT);
    }
    public void update(TipoInstrumento tipoInstrumento) {
        list.replaceAll(i -> i.getCodigo().equals(tipoInstrumento.getCodigo()) ? tipoInstrumento : i);
        setChanged();
        notifyObservers(LIST);
    }


    public static int NONE=0;
    public static int LIST=1;
    public static int CURRENT=2;

}
