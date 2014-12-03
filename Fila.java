import javax.swing.*;

public class Fila{
	int inicio = 0;
	int fim = -1;
	int tamanho = 52;
	int total = 0;
	Carta[] vet = new Carta[52];

	Fila(){
		for(int i = 0; i <= vet.length -1; i++){
			vet[i] = new Carta(0,' ',true,' ');
		}
	}

	public boolean Tavazia(){
		if(total <= 0){
			return true;
		}
		else{
			return false;		}
	}

	public boolean Tacheia(){
		if(total >= tamanho){
			return true;
		}
		else{
			return false;
		}
	}

	public void Enfileirar(Carta cartinha){
		if(Tacheia()){
			JOptionPane.showMessageDialog(null,"Ta cheia","Fila",1);
		}
		else{
			fim = fim + 1;
		}
		if(fim >= tamanho){
			fim = 0;
		}
		vet[fim] = cartinha;
		total = total + 1;
	}

	public Carta Desenfileirar(){
		if(Tavazia()){
			JOptionPane.showMessageDialog(null,"Ta vazia");
			return null;
		}
		else{
			total = total - 1;
			inicio = inicio + 1;
			if(inicio >= tamanho){
				inicio = 0;
				return vet[tamanho-1];
			}
			return vet[inicio -1];
		}
	}

	public void MostraFila(){
		int cont = 0;
		int pos = inicio;
		String msg = " ";
		while(cont < total){
			if(pos >= tamanho -1){
				pos = 0;
			}
			msg = msg +" "+ vet[pos].numero+""+vet[pos].naipe;
			pos = pos + 1;
			cont = cont + 1;
		}
		JOptionPane.showMessageDialog(null, msg);
	}

	public Carta PrimeiroElemento(){
		if(Tavazia()){
			return null;
		}
		else{
			return vet[inicio];
		}
	}
}
