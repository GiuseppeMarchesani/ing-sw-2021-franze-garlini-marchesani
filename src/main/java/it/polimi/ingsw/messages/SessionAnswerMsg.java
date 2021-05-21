package it.polimi.ingsw.messages;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.UUID;

public class SessionAnswerMsg extends AnswerMsg{
    UUID parentIdentifier;
    enum Result{JOINED, FAILED, CREATED};
    Result result;
    /**
     * Initializes the answer message.
     * @param parent The CommandMsg being answered.
     */
    public SessionAnswerMsg(CommandMsg parent , Result success)
    {

        super(parent);
        this.result=success;
    }



    /**
     * Returns the identifier of the message being answered.
     * @return The UUID of the answered message.
     */
    public UUID getParentIdentifier()
    {
        return parentIdentifier;
    }
    public void processMessage(ObjectOutputStream output) throws IOException{
        Scanner scanner = new Scanner(System.in);
        switch (result) {
            case FAILED:
                System.out.println("Errore nella creazione del server.\n");
                System.out.println("Inserisci l'id della partita.");
                String gameId = scanner.nextLine();
                output.writeObject((Object) new SessionMsg(gameId));
                break;
            case JOINED:
                System.out.println("Unione alla partita avvenuta con successo.\n");
                System.out.println("Attendi che l'host avvii la partita...\n");
                break;
            case CREATED:
                System.out.println("Partita creata con successo.\n");
                System.out.println("Premi Invio per iniziare la partita.\n");
                scanner.nextLine();
                // output.writeObject((Object) new StartGameMsg(,MessageType.START_GAME));
        }

    }
}
