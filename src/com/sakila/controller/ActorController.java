package com.sakila.controller;

import com.sakila.data.Actor;
import com.sakila.model.ActorModel;

import java.util.List;

public class ActorController {

    private final ActorModel model = new ActorModel();

    public List<Actor> listarTodos() {
        return model.getAll();
    }

    public List<Actor> buscarPorNombre(String texto) {
        return model.search(texto);
    }

    public boolean crearActor(String firstName, String lastName) {
        Actor actor = new Actor();
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        return model.insert(actor);
    }

    public boolean actualizarActor(int id, String firstName, String lastName) {
        Actor existing = model.getById(id);
        if (existing == null) {
            return false;
        }
        existing.setFirstName(firstName);
        existing.setLastName(lastName);
        return model.update(existing);
    }

    public boolean eliminarActor(int id) {
        return model.delete(id);
    }

    public String getLastError() {
        return model.getLastMessage();
    }
}
