public class Utilisateur  {
    protected String nom;
    protected String prenom;
    protected String IDE;
    protected String Password;
    protected EnumSexe sexe;
    
    
    
  
   
    //Constructeur par défaut
    public Utilisateur() {}
    
    /* Constructeur avec paramètres */
    public Utilisateur(String nom, String prenom, String IDE, String Password,EnumSexe sexe) {
        this.nom = nom;
        this.prenom = prenom;
        this.IDE = IDE;
        this.Password = Password;
        this.sexe = sexe;
        
       
          
    }

    
    public String getNom() {
        return nom;
    }

   
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    public String getPrenom() {
        return prenom;
    }

    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    
    public String getIDE() {
        return IDE;
    }

   public void setIDE(String IDE) {
        this.IDE = IDE;

    }
     
    public String getPassword(){
        return Password;}   

    public void setPassword(String Password){
     this.Password=Password;
    }

    public EnumSexe getSexe() {
        return sexe;
    }

    public void setSexe(EnumSexe sexe) {
        this.sexe = sexe;
    }

   
     

    
    

    
        
}    
