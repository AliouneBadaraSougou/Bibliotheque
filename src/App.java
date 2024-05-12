
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        // Create a new instance of the 'App' class
        Maclasse app = new Maclasse("Aliou", 25);

        System.out.println("nom:"+app.getNom()+"Age:"+app.getAge());




        String Time = EnumTime.INSTANCE.getCurrentDateTime();
        System.out.println(Time);
 
        }
        
        


    }


class Maclasse{
    private String nom; 
    private int age;
    //Constructeur par défaut
    public Maclasse(String nom, int age){
        this.nom = nom;
        this.age = age;
        }
        public String getNom(){
            return nom;
        }
        public int getAge() { 
            return age;
            }
        }


        

        public EnumTime gettTime() {
            return time;
        }
    
        public void setTime(EnumTime time) {
            this.time = time;}