package backend.lectortxt.controllers;

import backend.Estudiante;
import backend.interfaces.Identificable;

import java.io.*;

public class ControladorArchivoBinario {

    public void escribirObjeto(String nombreArchivo, Object objeto){
        try {
            File file = new File(nombreArchivo);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(objeto);
            objectOutputStream.close();
            System.out.println("objeto agregado " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Archivo no encontrado");
        }
    }

    public Identificable leerObjeto(String nombreArchivo){
        try {
            File file = new File(nombreArchivo);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Identificable objeto = (Identificable) objectInputStream.readObject();
            objectInputStream.close();
            return objeto;
        } catch (IOException e) {
            System.out.println("Archivo no encontrado");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada");
        }
        return null;
    }
}
