package com.example.adminlocal.group_festival;

/**
 * Created by adminlocal on 10/12/2017.
 */

public class Group  {
    private int id;
    private String artiste;
    private String texte;
    private String web;
    private String image;
    private String scene;
    private String jour;
    private String heure;


    public Group()
    {

    }
    public Group(int id,String artiste,String texte,String web,String image ,String scene ,String jour,String heure)
    {
        this.id=id;
        this.artiste=artiste;
        this.texte=texte;
        this.web=web;
        this.image=image;
        this.scene=scene;
        this.jour=jour;
        this.heure=heure;

    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }
    public String getArtiste() {
        return artiste;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
    public String getTexte() {
        return texte;
    }

    public void setImage(String Image) {
        this.image = image;
    }
    public String getImage() {
        return image;
    }

    public void setWeb(String web) {
        this.web = web;
    }
    public String getWeb() {
        return web;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }
    public String getScene() {
        return scene;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }
    public String getJour() {
        return jour;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
    public String getHeure() {
        return heure;
    }

}

