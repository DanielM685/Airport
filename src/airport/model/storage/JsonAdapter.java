/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import org.json.JSONObject;

/**
 *
 * @author user
 */
public interface JsonAdapter<T> {
    
    T fromJson(JSONObject jsonObject);
}
