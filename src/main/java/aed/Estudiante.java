package aed;

import java.util.ArrayList;
import java.util.List;

//Invariante :
//- raiz no es nulo
//- raiz cumple el invariante de NodoEst
//- los nodos estan ordenados lexicograficamente
//- todos los v de los hijos de un nodo son diferentes
//- la altura del trie no supera al mayor tamaño de algun estudiante
//- El nodo raíz no tiene significado

public class Estudiante{//trie
    
    NodoEst raiz;

    public Estudiante(){//O(1)
        raiz = new NodoEst('$');//O(1)
    }
    /**
     * O(1)
     */
    public NodoEst insert(String est){//O(|est|) == O(1) ya que est esta acotado
        NodoEst node = raiz;//O(1)
        for (char ch : est.toCharArray())//O(|est|) == O(1)
        {
            NodoEst n = null;//O(1)
            for (NodoEst hijo : node.hijos){//O(1) ya que la cantidad de hijos esta acotada por el / + numeros
                if(hijo.v == ch){//O(1) 
                    n = hijo;//O(1) 
                    break;//O(1) 
                }
            }
            if(n == null){//O(1) 
                NodoEst newhijo = new NodoEst(ch);//O(1) 
                node.addHijo(newhijo);//O(1)
                node = newhijo;//O(1) 
            }else{
                node = n;//O(1) 
            }
         
        }
        node.last = true;//O(1) 
        node.materias = 0;//O(1) 
        node.hijos = null;
        return node;//O(1) 
    }
    /**
     * O(1)
     */
    public NodoEst buscar(String est){//O(|est|) == O(1) ya que est esta acotado
        NodoEst nodo = raiz;//O(1) 
        for (char ch : est.toCharArray()) //O(|est|) == O(1)
        {
            NodoEst posible = null;//O(1) 
            for (NodoEst hijo : nodo.hijos)//O(1) ya que la cantidad de hijos esta acotada por el / + numeros
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
     * O(1)
     */
    public void inscribir(String estudiante){//O(1)
        NodoEst node = buscar(estudiante);//O(1)
        node.materias++;//O(1)
    }
    /**
     * O(1)
     */
    public void desinscribir(String estudiante){//O(1)
        NodoEst node = buscar(estudiante);//O(1)
        node.materias--;//O(1)
    }
    /**
     * O(1)
     */
    public int materias_inscriptas(String estudiante){//O(1)
        NodoEst student = buscar(estudiante);//O(1)
        return student.materias;//O(1)
    }
     /**
     * O(1)
     */
    public String[] get_inscriptos() {//O(1)
        List<String> words = new ArrayList<>();//O(1)
        getinscriptos(raiz, "", words);//O(1)

        return words.toArray(new String[0]);//O(1)
    }
    /**
     * O(1)
     */
    private void getinscriptos(NodoEst node, String prefix, List<String> words) {//O(k + n) las dos estan acotadas, entonces seria O(1)
        if (node.last) {//O(1)
            words.add(prefix);//O(k) donde k es la longitud de la palabra
        }
        for (NodoEst child : node.hijos) {// O(n) donde n es el número de hijos
            getinscriptos(child, prefix + child.v, words);// Recursión O(n + k)
        }
    } 


    // Invariante:
    // nodo.valor es un char.
    // nodo.last es true si y solo si nodo.hijos es null o vacio
    // los nodos sin significado siempre tienen hijos.
    class NodoEst{

        char v;
        boolean last;
        ArrayList<NodoEst> hijos;
        int materias;

        public NodoEst(char v) {//O(1) ya que todas las operaciones lo son
            this.v = v;//O(1)
            last = false;//O(1)
            hijos = new ArrayList<>();//O(1)
            materias = 0;//O(1)
        }

        public NodoEst getChild(char c) {//O(1)
            for (NodoEst child : hijos) {//O(k) k = cantidad de letras del alfabeto*2 + tildes, acotado, O(1)
                if (child.v == c) {//O(1)
                    return child;//O(1)
                }
            }
            return null;//O(1)
        }
    
        public void addHijo(NodoEst hijo) {//O(1)
            int pos = 0;//O(1)
            while (pos < hijos.size() && hijos.get(pos).v < hijo.v) {//las operaciones son O(1) y lo hace k veces, con k la cantidad de hijos
                                                                    ///que seria el los numeros, que es acotado -> O(1)
                pos++;//O(1)
            }
            hijos.add(pos, hijo);//O(1) 
        }


    }
}
