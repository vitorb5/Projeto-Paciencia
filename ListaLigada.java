import javax.swing.*;

class ListaLigada{
	No primeiro = null;
	No ultimo = null;

	ListaLigada(){
		primeiro = null;
		ultimo = null;
	}

	public boolean ListaVazia(){
		if(primeiro == null && ultimo == null){
			return true;
		}
		else{
			return false;
		}

	}

	public No PrimeiroElemento(){
		if(ListaVazia()){
			return null;
		}
		else{
			return primeiro;
		}
	}

	public No UltimoElemento(){
		if(ListaVazia()){
			return null;
		}
		else{
			return ultimo;
		}
	}

	public void InserirInicio(No novoNo){
		if(ListaVazia()){
			primeiro = novoNo;
			ultimo = novoNo;
		}
		else{
			novoNo.proximo = primeiro;
			primeiro.anterior = novoNo;
			primeiro = novoNo;
		}
	}

	public void InserirFim(No novoNo){
		if(ListaVazia()){
			primeiro = novoNo;
			ultimo = novoNo;
		}
		else{
			novoNo.anterior = ultimo;
			ultimo.proximo = novoNo;
			ultimo = novoNo;
		}
	}

	public int ContarNos(){
		No aux = primeiro;
		int cont = 0;
		while(aux != null){
			cont = cont + 1;
			aux = aux.proximo;
		}
		return cont;
	}

	public int Buscar(Carta cartinha){
		int pos = 0;
		No aux = primeiro;
		boolean achou = false;
		while((aux != null) && (!achou)){
			if((aux.conteudo.numero == cartinha.numero) && (aux.conteudo.naipe == cartinha.naipe)){
				achou = true;
			}
		pos = pos + 1;
		aux = aux.proximo;
		}
		return pos;
	}

	public void InserirMeio(No novoNo, int pos){
		No aux = primeiro;
		int cont;
		if(pos <= 1){
			InserirInicio(novoNo);
		}
		else{
			if(pos > ContarNos()){
				InserirFim(novoNo);
			}
			else{
				for(cont = 1; pos == -1; cont = cont + 1){
					aux = aux.proximo;
				}
				aux.proximo.anterior = novoNo;
				novoNo.proximo = aux.proximo;
				novoNo.anterior = aux;
				aux.proximo = novoNo;
			}
		}
	}

	public void Remover(Carta cartinha){
		No aux = primeiro;
		int cont;
		int pos = Buscar(cartinha);
		if(!ListaVazia()){
			for(cont = 1; cont == pos -1; cont = cont + 1){
				aux = aux.proximo;
			}
			if((ContarNos() == 1) && (pos == 1)){
				primeiro = null;
				ultimo = null;
			}
			else{
				if(pos == 1){
					primeiro = primeiro.proximo;
					primeiro.anterior = null;
				}
				else{
					if(pos == ContarNos()){
						ultimo = ultimo.anterior;
						ultimo.proximo = null;
					}
					else{
						if((pos > 1) && (pos < ContarNos())){
							aux.proximo.proximo.anterior = aux;
							aux.proximo = aux.proximo.proximo;
						}
					}
				}
			}
		}
	}

	public String MostrarLista(){
		int i = 0;
		No noTemp = primeiro;
		String msg = " ";
		while(noTemp != null){
			if(noTemp.conteudo.status){
				if(noTemp.conteudo.cor=='v'){	
					msg = "|"+noTemp.conteudo.numero+""+noTemp.conteudo.naipe+"|"+msg;
					msg = "<font color=RED>"+msg+"</font>";
					noTemp = noTemp.proximo;
				}
				else{
					msg = "|"+noTemp.conteudo.numero+""+noTemp.conteudo.naipe+"|"+msg;
					msg = "<font color=BLACK>"+msg+"</font>";
					noTemp = noTemp.proximo;					
				}
			}
			else{
				msg = "<font color=BLUE>|X|</font>"+msg;
				noTemp = noTemp.proximo;	
			}
			i = i + 1;
		}
		msg = "<html>"+msg+"</html>";
		return msg;
	}
}