import javax.swing.*;

public class Pilha{
	int tamanho;
	int topo = -1;
	Carta[] vet;

	Pilha(int tama){
		tamanho = tama;
		vet = new Carta[tamanho];
		for(int i = 0; i <= vet.length -1; i++){
			vet[i] = new Carta(0,' ',false,' ');
		}
	}
	

	public boolean Tavazio(){
		if(topo <= -1){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean Tacheio(){
		if(topo >= tamanho -1){
			return true;
		}
		else{
			return false;
		}
	}

	public void Empilhar(Carta cartinha){
		if(Tacheio()){
			System.out.println("A pilha esta cheia");
		}
		else{
			topo = topo + 1;
			vet[topo] = cartinha;
		}
	}

	public Carta Desempilhar(){
		if(Tavazio()){
			System.out.println("A pilha esta vazia");
			return null;
		}
		else{
			topo = topo - 1;
			return vet[topo + 1];
		}
	}

	public Carta CartaTopo(){
		if(Tavazio()){
			//System.out.println("A pilha esta vazia");
			return null;
		}
		else{
			return vet[topo];
		}
	}

	public String CartaTopoImprime(){
		String msg = "";
		if(Tavazio()){
			msg = "<font color=BLUE>|X|</font>";
		}
		else{
			if(vet[topo].cor=='v'){
				msg = "<font color=RED>|"+vet[topo].numero+""+vet[topo].naipe+"|</font>";
			}
			else{
				msg = "<font color=BLACK>|"+vet[topo].numero+""+vet[topo].naipe+"|</font>";
			}
		}
		return msg;
	}

	public void MostrarPilha(){
		int cont = topo;
		for(cont = topo; cont == 0; cont = cont + 1){
			System.out.print(vet[cont]+" ");
		}
		
	}
}
