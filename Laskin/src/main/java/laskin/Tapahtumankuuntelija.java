package laskin;

import java.util.HashMap;
import java.util.Map;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Tapahtumankuuntelija implements EventHandler {
    private TextField tuloskentta; 
    private TextField syotekentta; 
    private Button plus;
    private Button miinus;
    private Button nollaa;
    private Button undo;
    private Sovelluslogiikka sovellus;

    private Map<Button, Komento> komennot;
    private Komento edellinen = null;
 

    public Tapahtumankuuntelija(TextField tuloskentta, TextField syotekentta, Button plus, Button miinus, Button nollaa, Button undo) {
        this.undo = undo;
        this.sovellus = new Sovelluslogiikka();
        komennot = new HashMap<>();
        komennot.put(plus, new Summa(tuloskentta, syotekentta,  nollaa, undo, sovellus) );
        komennot.put(miinus, new Erotus(tuloskentta, syotekentta, nollaa, undo, sovellus) );
        komennot.put(nollaa, new Nollaa(tuloskentta, syotekentta,  nollaa, undo, sovellus) );
    }
    
    @Override
    public void handle(Event event) {
        if ( event.getTarget() != undo ) {
            Komento komento = komennot.get((Button)event.getTarget());
            komento.suorita();
            edellinen = komento;
        } else {
            edellinen.peru();
            edellinen = null;
        }                  
    }

	private static class Summa implements Komento {
		TextField tuloskentta;
		TextField syotekentta;
		Button nollaa;
		Button undo;
		Sovelluslogiikka sovellus;
		int prev = 0;

		public Summa(TextField tuloskentta, TextField syotekentta, Button nollaa, Button undo, Sovelluslogiikka sovellus) {
			this.tuloskentta = tuloskentta;
			this.syotekentta = syotekentta;
			this.nollaa = nollaa;
			this.undo = undo;
			this.sovellus = sovellus;
		}

		@Override
		public void suorita() {
			prev = sovellus.tulos();
			int arvo = 0;
			try {
				arvo = Integer.parseInt(syotekentta.getText());
			} catch (Exception e) {
			}
			sovellus.plus(arvo);
			int laskunTulos = sovellus.tulos();
    
			syotekentta.setText("");
			tuloskentta.setText("" + laskunTulos);
			
			if ( laskunTulos==0) {
				nollaa.disableProperty().set(true);
			} else {
				nollaa.disableProperty().set(false);
			}
			undo.disableProperty().set(false);
		}

		@Override
		public void peru() {
			sovellus.nollaa();
			sovellus.plus(prev);
			tuloskentta.setText("" + sovellus.tulos());
		}
	}

	private static class Erotus implements Komento {
		TextField tuloskentta;
		TextField syotekentta;
		Button nollaa;
		Button undo;
		Sovelluslogiikka sovellus;
		int prev = 0;

		public Erotus(TextField tuloskentta, TextField syotekentta, Button nollaa, Button undo, Sovelluslogiikka sovellus) {
			this.tuloskentta = tuloskentta;
			this.syotekentta = syotekentta;
			this.nollaa = nollaa;
			this.undo = undo;
			this.sovellus = sovellus;
		}

		@Override
		public void suorita() {
			prev = sovellus.tulos();
			int arvo = 0;
			prev = sovellus.tulos();
			try {
				arvo = Integer.parseInt(syotekentta.getText());
			} catch (Exception e) {
			}
			sovellus.miinus(arvo);
			int laskunTulos = sovellus.tulos();
    
			syotekentta.setText("");
			tuloskentta.setText("" + laskunTulos);
			
			if ( laskunTulos==0) {
				nollaa.disableProperty().set(true);
			} else {
				nollaa.disableProperty().set(false);
			}
			undo.disableProperty().set(false);
		}

		@Override
		public void peru() {
			sovellus.nollaa();
			sovellus.plus(prev);
			tuloskentta.setText("" + sovellus.tulos());
		}
	}

	private static class Nollaa implements Komento {
		TextField tuloskentta;
		TextField syotekentta;
		Button nollaa;
		Button undo;
		Sovelluslogiikka sovellus;
		int prev = 0;

		public Nollaa(TextField tuloskentta, TextField syotekentta, Button nollaa, Button undo, Sovelluslogiikka sovellus) {
			this.tuloskentta = tuloskentta;
			this.syotekentta = syotekentta;
			this.nollaa = nollaa;
			this.undo = undo;
			this.sovellus = sovellus;
		}

		@Override
		public void suorita() {
			prev = sovellus.tulos();
			sovellus.nollaa();
			int laskunTulos = sovellus.tulos();
    
			syotekentta.setText("");
			tuloskentta.setText("" + laskunTulos);
			
			nollaa.disableProperty().set(true);
			undo.disableProperty().set(false);
		}

		@Override
		public void peru() {
			sovellus.nollaa();
			sovellus.plus(prev);
			tuloskentta.setText("" + sovellus.tulos());
		}
	}


}