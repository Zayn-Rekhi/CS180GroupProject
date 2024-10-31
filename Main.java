import java.io.*;

  
public class Main { 
    public static void main(String[] args) 
        throws IOException, ClassNotFoundException 
    { 
        User wali = new User("walle", "california", "hi i'm ujjawal"); 
        Comment walisComment = new Comment(wali, "i like to sleep", "10/31/2024");
        Comment walisComment2 = new Comment(wali, "I am brayden's roomate", "10/31/2024");

        walisComment.editMessage("i like to sleep even more now", "11/1/2024");
  
        // Serializing 'a' 
        FileOutputStream fos 
            = new FileOutputStream("ujw.txt"); 
        ObjectOutputStream oos 
            = new ObjectOutputStream(fos); 
        oos.writeObject(walisComment); 
        oos.writeObject(walisComment2); 
  
        // De-serializing 'a' 
        FileInputStream fis 
            = new FileInputStream("ujw.txt"); 
        ObjectInputStream ois = new ObjectInputStream(fis); 
        Comment walisCommentAgain = (Comment) ois.readObject();
        Comment walisComment2Again = (Comment) ois.readObject(); // down-casting object 
  
        System.out.println(walisCommentAgain.displayedComment()); 
        System.out.println(walisComment2Again.displayedComment()); 



  
        // closing streams 
        oos.close(); 
        ois.close(); 
    } 
}