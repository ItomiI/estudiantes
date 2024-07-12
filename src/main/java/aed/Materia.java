package aed;

import java.util.ArrayList;
import java.util.List;

import aed.SistemaSIU.CargoDocente;

//Invariante :
//- raiz no es nulo
//- raiz cumple el invariante de NodoMat
//- los nodos estan ordenados lexicograficamente
//- todos los nodo.v de los hijos de un nodo son diferentes
//- El nodo raíz no tiene significado

public class Materia {
    
    NodoMat raiz;


    public Materia(){
        raiz = new NodoMat('$');//O(1)
    }
    /**
     * O(|mat|)
     */
    public NodoMat insert(String mat,ArrayList<TuplaMatCar> vari,Numero num,int[] docentes,ArrayList<String> estudiantes){//O(|mat|) el largo del nombre de la materia
        NodoMat node = raiz;//O(1)
        for (char ch : mat.toCharArray())//O(|mat|) 
        {
            NodoMat n = null;//O(1)
            for (NodoMat hijo : node.hijos){//O(1) ya que la cantidad de hijos esta acotada por la cantidad de letras  
                if(hijo.v == ch){//O(1) 
                    n = hijo;//O(1) 
                    break;//O(1) 
                }
            }
            if(n == null){//O(1) 
                NodoMat newhijo = new NodoMat(ch);//O(1) 
                node.addHijo(newhijo);//O(1)
                node = newhijo;//O(1) 
            }else{
                node = n;//O(1) 
            }
        }
        node.last = true;//O(1) 
        node.estudiantes = estudiantes;//O(1)
        node.docentes =  docentes;//O(1)
        node.variaciones = vari;//O(1)
        node.inscriptos = num;
        return node;//O(1) 
    }
    /**
     * O(|mat|)
     */
    public NodoMat buscar(String mat){//O(|mat|)
        NodoMat nodo = raiz;//O(1) 
        for (char ch : mat.toCharArray()) //O(|mat|) 
        {
            NodoMat posible = null;//O(1) 
            for (NodoMat hijo : nodo.hijos)//O(1) ya que la cantidad de hijos esta acotada por las letras de alfabeto 
            {
                if(hijo.v == ch)//O(1)
                {
                    posible = hijo;//O(1)
                }
            }
            if(posible == null){//O(1)
                return null;//O(1)
            }else{
                nodo = posible;//O(1)
            }
        }
        return nodo;//O(1)
    }
    /**
     * //O(|materia|)
     */
    public void inscribir(String materia,String estudiante){//O(|materia|)
        NodoMat la_materia = buscar(materia);//O(|materia|)
        la_materia.estudiantes.add(estudiante);//O(1)
        la_materia.inscriptos.mas_uno();
    
    }
    /**
     * //O(|materia|)
     */
    public void agregar_docente(String materia,CargoDocente cargo){//O(|materia|)
        NodoMat mat = buscar(materia);//O(|materia|)
        int d = docente_int(cargo);//O(1)
        mat.docentes[d] +=1;//O(1)
    }
    /**
     * //O(|materia|)
     */
    public int[] get_docentes(String materia){//O(|materia|)
        NodoMat mat = buscar(materia);//O(|materia|)
        return mat.docentes;//O(1)
    }

    private int docente_int(CargoDocente cargo){//O(1)
        switch (cargo) {//O(1)
         case PROF:
             return 0;//O(1)
         case JTP:
             return 1;//O(1)
         case AY1:
             return 2;//O(1)
         case AY2:
             return 3;//O(1)
         default:
             return 45235827;//O(1)
        }
 
     } 
    /**
     * O(sum(|materias|))
    */
    public String[] las_materias() {//O(sum(|materias|))
        List<String> words = new ArrayList<>();
        get_materias(raiz, "", words);
        return words.toArray(new String[0]);
    }

    private void get_materias(NodoMat node, String prefix, List<String> words) {
        if (node.last) {
            words.add(prefix);
        }
        for (NodoMat child : node.hijos) {
            get_materias(child, prefix + child.v, words);
        }
    }  

    public void eliminar_materia(String materia){//O(|materia|)
        eliminar_materia(raiz, materia,0);//O(|materia|)
    }

    private boolean eliminar_materia(NodoMat actual,String materia,int index){//O(|materia|)
        if (index == materia.length()) { // Caso base //O(1)
            if (!actual.last) {//O(1)
                return false;//O(1)
            }
            actual.last = false; //O(1)
            return actual.hijos.size() == 0; //O(1)
        }
        char c = materia.charAt(index);//O(1)
        NodoMat hijo = actual.getChild(c);//O(1)
        if (hijo == null) {//O(1)
            return false; //O(1)
        }

        boolean hay_que_eliminar = eliminar_materia(hijo, materia, index + 1); // O(|materia|)

        if (hay_que_eliminar) {//O(1)
            actual.removeHijo(c);//O(1)
            return actual.hijos.isEmpty() && !actual.last; //O(1)
        }
        return false;//O(1)
    }



    // Invariante:
    // nodo.valor es un char.
    // si nodo.last es false entonces nodo.hijos no esta vacio
    // si nodo.last es true entonces :
    // - nodo.docentes no es null y en cada posicion es mayor o igual a 0
    // - nodo.estudiantes no es null
    // - nodo.inscriptos es un numero positivo igual a estudiantes.size()
    // - nodo.variaciones son todos los nombre de la materia


    class NodoMat{

        char v;
        ArrayList<NodoMat> hijos;

        boolean last;

        //significado
        int[] docentes;// 
        ArrayList<String> estudiantes;
        Numero inscriptos; // |estudiantes|
        ArrayList<TuplaMatCar> variaciones;
        
        public NodoMat(char v) {//O(1) ya que todas las operaciones lo son
            this.v = v;//O(1)
            last = false;//O(1)
            hijos = new ArrayList<>();//O(1)
            variaciones = null;
            docentes = null;//O(1)
            estudiantes = null;//O(1)
            inscriptos = null;
        }

        public int[] docentes(){
            return docentes;
        }

        public ArrayList<String> estudiantes(){//O(1)
            return estudiantes;
        }

        public ArrayList<TuplaMatCar> get_variaciones(){//O(1)
            return variaciones;
        }

        public int inscriptos(){//O(1)
            return inscriptos.numero();
        }

        public NodoMat getChild(char c) {//O(1)
            for (NodoMat child : hijos) {//O(k) k = cantidad de letras del alfabeto*2 + tildes, acotado, O(1)
                if (child.v == c) {//O(1)
                    return child;//O(1)
                }
            }
            return null;//O(1)
        }
    
        public void addHijo(NodoMat hijo) {//O(1)
            int pos = 0;//O(1)
            while (pos < hijos.size() && hijos.get(pos).v < hijo.v) {//las operaciones son O(1) y lo hace k veces, con k la cantidad de hijos
                                                                    ///que seria el alfabeto*2 + tildes, que es acotado -> O(1)
                pos++;//O(1)
            }
            hijos.add(pos, hijo);//O(1) 
        }

        public void removeHijo(char c) {//O(1)
            int pos = 0;//O(1)
            while (pos < hijos.size() && hijos.get(pos).v < c) {//las operaciones son O(1) y lo hace k veces, con k la cantidad de hijos
                                                                ///que seria el alfabeto*2 + tildes, que es acotado -> O(1)
                pos++;//O(1)
            }
            hijos.remove(pos);//O(|hijos|), acotado -> O(1)
        }

    }

}
