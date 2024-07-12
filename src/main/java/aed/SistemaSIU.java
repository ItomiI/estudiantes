package aed;

import java.util.ArrayList;

import aed.Carreras.NodoCar;
import aed.Materia.NodoMat;

//C = cantidad de carreras
//E = cantidad de Estudiantes
//M = cantidad de materias
//M_c = cantidad de materias de la carrera c
//N_m = cantidad de nombres de la materia m
//E_m = cantidad de estudiantes de la materia m


//Invariante de Representacion: 
//carreras cumple el invariante de Carreras y estudiantes cumple el invariante de Estudiante

public class SistemaSIU {

    public enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    Estudiante trieEstudiantes;
    Carreras trieCarreras;

    //cada carrera se busca e inserta en tiempo O(|carrera|), las veces que salga con el nombre de una materia
    //entonces cada carrera c, se buscaria e insertaria en O(|C| * |M_c|)

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){//O(E + sum_c_C(|c| * |M_c|) + sum_m_M(sum_n_M(|n|)))
        trieEstudiantes = new Estudiante();//(1)
        for(String libreta : libretasUniversitarias)//cantidad de libretas O(E)
        {
            trieEstudiantes.insert(libreta);// O(1) ya que esta acotado
        } 
        ///////
        trieCarreras = new Carreras();
        for(InfoMateria infomat : infoMaterias){//O(M)

            ArrayList<TuplaMatCar> variaciones =  new ArrayList<>();//O(1)
            Numero num = new Numero();//O(1)
            int[] docentes = new int[]{0,0,0,0};//O(1)
            ArrayList<String> estudents = new ArrayList<>();

            for(ParCarreraMateria par : infomat.getParesCarreraMateria()){//O(N_m)

                String car = par.getCarrera();
                NodoCar nodocar = trieCarreras.buscar(car);//O(|carrera|)

                if(nodocar == null)//O(1)
                {
                    nodocar = trieCarreras.insert(par.getCarrera());//O(|carrera|)
                }
                variaciones.add(new TuplaMatCar(par.getNombreMateria(),nodocar.materiastrie));//O(1)

                trieCarreras.insert_materia(nodocar, par.getNombreMateria(),variaciones,num,docentes,estudents);//O(|N_m|)
                
            }
        }

    }

    public void inscribir(String estudiante, String carrera, String materia){//O(|materia|) + O(|carrera|) 
        trieEstudiantes.inscribir(estudiante);//O(1)
        trieCarreras.inscribir(estudiante, carrera, materia);//O(|materia|) + O(|carrera|)
    }

    public int inscriptos(String materia, String carrera){//O(|carrera|) + O(|materia|)
        NodoCar car = trieCarreras.buscar(carrera);//O(|carrera|)
        NodoMat mat = car.materiastrie.buscar(materia);//O(|materia|)

        return mat.inscriptos();//O(1) 
    }
    
    public void agregarDocente(CargoDocente cargo, String carrera, String materia){//O(|carrera|) + O(|materia|)
        NodoCar car = trieCarreras.buscar(carrera);//O(|carrera|)
        car.materiastrie.agregar_docente(materia,cargo);//O(|materia|)
    }

    public int[] plantelDocente(String materia, String carrera){
        NodoCar car = trieCarreras.buscar(carrera);//O(|carrera|)
        return car.materiastrie.get_docentes(materia);//O(|materia|)   
    }

    public boolean excedeCupo(String materia, String carrera){//O(|carrera|) + O(|materia|)
        NodoCar car = trieCarreras.buscar(carrera);//O(|carrera|)
        NodoMat mat = car.materiastrie.buscar(materia);//O(|materia|)
        int[] plantel = mat.docentes();//O(1)
        int cupos = max(max(plantel[0] * 250, 100 * plantel[1]),max( 20 * plantel[2],30 * plantel[2]));//O(1)
        return mat.inscriptos() > cupos;//O(1)
    }

    public String[] carreras(){//O(sum(|car|))
        return trieCarreras.las_carreras();//O(sum(|car|))
    }

    public String[] materias(String carrera){//O(|carrera|) + O(sum(|materias|))
        NodoCar car = trieCarreras.buscar(carrera);//O(|carrera|)
        String [] mats = car.materiastrie.las_materias();//O(sum(|materias|))
        return mats;
    }

    public int materiasInscriptas(String estudiante){//O(1)
        int m =  trieEstudiantes.materias_inscriptas(estudiante);//O(1)
        return m;//O(1)
    }

    public void cerrarMateria(String materia, String carrera){//O(|carrera|) + O(|materia|) + O(E_m) + O(sum(|materia|))
        NodoCar car = trieCarreras.buscar(carrera);//O(|carrera|)
        NodoMat mat = car.materiastrie.buscar(materia);//O(|materia|)

        for(String est : mat.estudiantes){//O(E_m)
            trieEstudiantes.desinscribir(est);//O(1)
        }

        for(TuplaMatCar tup : mat.variaciones){//O(sum(|materia|))
            tup.get_trie_dela_carrera().eliminar_materia(tup.get_materia());//O(|materia|)
        }

    }
    
    private int max(int uno,int dos){//O(1)
        return uno < dos ? uno:dos;//O(1)
    }


}
