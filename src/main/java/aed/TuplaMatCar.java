package aed;

//Invariante :
//- triematerias es el trie de una carrera que contiene la materia 

public class TuplaMatCar {
    
    String materia;
    Materia triematerias;

    public TuplaMatCar(String materia,Materia trie_materias){
        this.materia = materia;
        triematerias = trie_materias;
    }
    
    public String get_materia(){
        return materia;
    }
    public Materia get_trie_dela_carrera(){
        return triematerias;
    }
    
}
