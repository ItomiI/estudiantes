package aed;

import java.util.ArrayList;
import java.util.List;

import aed.Materia.NodoMat;
//Invariante :
//- raiz no es nulo
//- raiz cumple el invariante de NodoCar
//- los nodos estan ordenados lexicograficamente
//- todos los v de los hijos de un nodo son diferentes
//- El nodo raíz no tiene significado

public class Carreras {
    
    NodoCar raiz;

    public Carreras(){
        raiz = new NodoCar('$');
    }
    /**
     * O(|car|)
     */
    public NodoCar insert(String car){//O(|car|) el largo del nombre de la materia
        NodoCar node = raiz;//O(1)
        for (char ch : car.toCharArray())//O(|car|) 
        {
            NodoCar n = null;//O(1)
            for (NodoCar hijo : node.hijos){//O(1) ya que la cantidad de hijos esta acotada por la cantidad de letras  
                if(hijo.v == ch){//O(1) 
                    n = hijo;//O(1) 
                    break;//O(1) 
                }
            }
            if(n == null){//O(1) 
                NodoCar newhijo = new NodoCar(ch);//O(1) 
                node.addHijo(newhijo);//O(1)
                node = newhijo;//O(1) 
            }else{
                node = n;//O(1) 
            }
         
        }
        node.last = true;//O(1) 
        node.materiastrie = new Materia();//O(1) 
        return node;//O(1) 
    }
    /**
     * //O(|car|)
     */
    public NodoCar buscar(String car){//O(|car|)
        NodoCar nodo = raiz;//O(1) 
        for (char ch : car.toCharArray()) //O(|car|) 
        {
            NodoCar posible = null;//O(1) 
            for (NodoCar hijo : nodo.hijos)//O(1) ya que la cantidad de hijos esta acotada por las letras de alfabeto 
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
     * O(|mat|)
     */
    public NodoMat insert_materia(NodoCar carrera, String mat,ArrayList<TuplaMatCar> variaciones,Numero num,int[] docentes,ArrayList<String> estudiantes){//O(|mat|)
        return carrera.materiastrie.insert(mat,variaciones,num,docentes,estudiantes);//O(|mat|)
    }
    /**
     * O(|materia|) + O(|carrera|)
     */
    public void inscribir(String estudiante,String carrera,String materia){//O(|materia|) + O(|carrera|)
        Materia materias_de_carrera = buscar(carrera).get_materias();//O(|carrera|) + O(1)
        materias_de_carrera.inscribir(materia,estudiante);//O(|materia|)
       
    }
    /**
     * O(sum(|carreras|))
     */
    public String[] las_carreras() {//O(sum(|carreras|))
        List<String> words = new ArrayList<>();
        getcarreras(raiz, "", words);
        return words.toArray(new String[0]);
    }
    private void getcarreras(NodoCar node, String prefix, List<String> words) {
        if (node.last) {
            words.add(prefix);
        }
        for (NodoCar child : node.hijos) {
            getcarreras(child, prefix + child.v, words);
        }
    }  




    // Invariante:
    // nodo.valor es un char.
    // nodo.last es false si y solo si (nodo.materiastrie es null y nodo.hijos no esta vacio). 
    // si nodo.last es true entonces nodo.materiastrie es diferente de null y cumple el invariante de Materia

    class NodoCar{

        char v;
        boolean last;
        ArrayList<NodoCar> hijos;
        Materia materiastrie;

        public NodoCar(char v) {//O(1) ya que todas las operaciones lo son
            this.v = v;//O(1)
            last = false;//O(1)
            hijos = new ArrayList<>();//O(1)
            materiastrie = null;//O(1)
        }

        public Materia get_materias(){
            return materiastrie;
        }
        public NodoCar getChild(char c) {//O(1)
            for (NodoCar child : hijos) {//O(k) k = cantidad de letras del alfabeto*2 + tildes, acotado, O(1)
                if (child.v == c) {//O(1)
                    return child;//O(1)
                }
            }
            return null;//O(1)
        }
    
        public void addHijo(NodoCar hijo) {//O(1)
            int pos = 0;//O(1)
            while (pos < hijos.size() && hijos.get(pos).v < hijo.v) {//las operaciones son O(1) y lo hace k veces, con k la cantidad de hijos
                                                                    ///que seria el alfabeto*2 + tildes, que es acotado -> O(1)
                pos++;//O(1)
            }
            hijos.add(pos, hijo);//O(1) 
        }


    }

}
