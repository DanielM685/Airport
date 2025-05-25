/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.json.JSONArray;
import org.json.JSONTokener;

/**
 *
 * @author user
 */
public class JsonRepository<T> {
    
    private final List<T> items = new ArrayList<>();

    public JsonRepository(String filePath, JsonAdapter<T> adapter) {
        try (FileReader reader = new FileReader(filePath)) {
            JSONArray array = new JSONArray(new JSONTokener(reader));
            for (int i = 0; i < array.length(); i++) {
                items.add(adapter.fromJson(array.getJSONObject(i)));
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado: " + filePath + ". Se iniciará vacío.");
        } catch (Exception e) {
            System.out.println("Error al leer " + filePath + ": " + e.getMessage());
        }
    }

    public List<T> getAll() {
        return new ArrayList<>(items); // copia de seguridad
    }

    public void add(T object) {
        items.add(object); // solo memoria
    }

    public void remove(T object) {
        items.remove(object);
    }

    public void clear() {
        items.clear();
    }
    
}
