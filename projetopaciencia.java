import javax.swing.*;

public class projetopaciencia{
	
	//Armazena as 52 cartas no baralho
	public static Carta[] Armazenamento(Carta baralho[]){
		for(int pos = 0; pos <= 12; pos = pos +1){
			baralho[pos]= new Carta(pos+1, 'c', true, 'v');
			baralho[pos+13] = new Carta(pos+1, 'e', true, 'p');
			baralho[pos+26] = new Carta(pos+1, 'p', true, 'p');
			baralho[pos+39] = new Carta(pos+1, 'o', true, 'v');
		}
		return  baralho;
	}

	//Função para embaralhar o baralho
	public static Carta[] EmbaralharRec(Carta baralho[], int cont){
		int op1 = 0, op2 = 0;
		Carta aux;
		if(cont != 1000){
			op1 = (int)(Math.random()*baralho.length);
			op2 = (int)(Math.random()*baralho.length);
			aux = baralho[op1];
			baralho[op1] = baralho[op2];
			baralho[op2] = aux;
			EmbaralharRec(baralho, cont+1);
		}
		return baralho;
	}

	//Função para mostrar o baralho
	public static void Mostrar(Carta baralho[]){
		String msg = "";
		for(int cont = 0; cont <= baralho.length -1; cont = cont + 1){
			msg = msg +" "+baralho[cont].numero + baralho[cont].naipe;
		}
		JOptionPane.showMessageDialog(null,msg);
	}

	//Verifica se e possivel armazenar as cartas dentro das pilhas
	public static boolean VerificaPilha(Carta cartinha, Pilha p){
		if((p.Tavazio() && cartinha.numero == 1) || (! p.Tavazio() && p.CartaTopo().naipe == cartinha.naipe && p.CartaTopo().numero == cartinha.numero -1)){
			return true;
		}
		else{
			return false;
		}
	}

	//Verifica se é possivel armazenar e/ou mover as cartas dentro das listas ligadas
	public static boolean VerificaListaLigada(Carta cartinha, ListaLigada lista){
		if(lista.ListaVazia()){
			if((cartinha.numero == 13) && (cartinha.status == true)){
				return true;
			}
			else{
				JOptionPane.showMessageDialog(null,"Não é possivel mover esta carta");
				return false;
			}
		}
		else{
			if((cartinha.cor != lista.PrimeiroElemento().conteudo.cor) && (cartinha.numero == lista.PrimeiroElemento().conteudo.numero -1) && (cartinha.status == true)){
				return true;
			}
			else{
				return false;
			}
		}
	}

	//Monta as listas ligadas com as cartas que estão na fila, deixando somente a ultima carta da lista "virada"
	public static ListaLigada MontandoLista(Fila monte, int tamanholista){
		ListaLigada aux = new ListaLigada();
		Carta cartinha = new Carta(0,' ',true,' ');
		No novoNo;
		for(int i = 0; i <= tamanholista -1; i++){
			cartinha = monte.Desenfileirar();
			if(i < tamanholista -1){
				cartinha.status = false;
			}
			else{
				cartinha.status = true;
			}
			novoNo = new No(cartinha);
			aux.InserirInicio(novoNo);
		}
		return aux;
	}

	//Usando o verificaPilha armazena uma carta do monte em uma pilha
	public static Pilha MonteparaPilha(Fila monte, Pilha pilha){
		if(VerificaPilha(monte.PrimeiroElemento(),pilha)){
			pilha.Empilhar(monte.Desenfileirar());
			return pilha;
		}
		else{
			JOptionPane.showMessageDialog(null,"Nao e possivel empilhar esta carta");
			return pilha;
		}	
	}	

	//Usando o VerificaListaLigada armazena uma carta do monte em uma lista ligada
	public static ListaLigada MonteparaListaLigada(Fila monte, ListaLigada lista){
		Carta cartinha = new Carta(0,' ',true,' ');
		No novoNo;
		if(VerificaListaLigada(monte.PrimeiroElemento(),lista)){
			cartinha = monte.Desenfileirar();
			novoNo = new No(cartinha);
			lista.InserirInicio(novoNo);
			return lista;
		}
		else{
			JOptionPane.showMessageDialog(null,"Nao e possivel inserir esta carta");
			return lista;
		}
	}

	//Usando o verifica pilha armazena uma carta da lista em uma pilha
	public static Pilha ListaparaPilha(ListaLigada lista, Pilha pilha){
		if(VerificaPilha(lista.PrimeiroElemento().conteudo,pilha)){
			pilha.Empilhar(lista.PrimeiroElemento().conteudo);
			lista.Remover(lista.PrimeiroElemento().conteudo);
			if(!lista.ListaVazia()){
				lista.PrimeiroElemento().conteudo.status = true;
			}
			return pilha;
		}
		else{
			JOptionPane.showMessageDialog(null,"Nao e possivel empilhar esta carta");
			return pilha;
		}
	}

	//Usando o VerificaLista move as cartas entre as listas ligadas
	public static ListaLigada ListaparaLista(ListaLigada saindo, ListaLigada entrando){
		int entrada = 0;
		int nos = 0;
		int sub = 0;
		int subAux = 0;
		No novoNo;
		Pilha aux = new Pilha(52);
		nos = saindo.ContarNos() + 1;
		entrada = Integer.parseInt(JOptionPane.showInputDialog(null,"Qual a posicao da carta?"));
		sub = nos - entrada;
		subAux = sub;
		for(int cont = 0; cont < sub; cont = cont + 1){
			aux.Empilhar(saindo.PrimeiroElemento().conteudo);
			saindo.Remover(saindo.PrimeiroElemento().conteudo);
		}
		if(VerificaListaLigada(aux.CartaTopo(), entrando)){
			while(sub != 0){
				novoNo = new No(aux.Desempilhar());
				entrando.InserirInicio(novoNo);
				if(!saindo.ListaVazia()){
					saindo.PrimeiroElemento().conteudo.status = true;
				}

				sub = sub - 1;
			}
			return entrando;
		}
		else{
			while(subAux != 0){
				novoNo = new No(aux.Desempilhar());
				saindo.InserirInicio(novoNo);
				subAux = subAux - 1;
			}
			return entrando;
		}
	}

	//Interface do Jogo
	public static int Interface(ListaLigada lista1,ListaLigada lista2,ListaLigada lista3,ListaLigada lista4,ListaLigada lista5,ListaLigada lista6,ListaLigada lista7,Pilha pilha1,Pilha pilha2,Pilha pilha3,Pilha pilha4,Fila monte){
		Carta filaPrimeiroElemento = monte.PrimeiroElemento();
		int op = 0;
		String filavirada = " ", espaco= "                               ";
		if(filaPrimeiroElemento.cor=='v'){
			filavirada = "<font color=BLUE>|X|</font><font color=RED>|"+filaPrimeiroElemento.numero+filaPrimeiroElemento.naipe+"|</font>";
		}
		else{
			filavirada = "<font color=BLUE>|X|</font><font color=BLACK>|"+filaPrimeiroElemento.numero+filaPrimeiroElemento.naipe+"|</font>";	
		}
	
		try{	
			op = Integer.parseInt(JOptionPane.showInputDialog(null,"<html>"+filavirada+
																"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
																pilha1.CartaTopoImprime()+
																pilha2.CartaTopoImprime()+
																pilha3.CartaTopoImprime()+
																pilha4.CartaTopoImprime()+"</html> \n\n\n\n"+
																"====================================\n"+
																lista1.MostrarLista()+"\n"+
																"====================================\n"+
																lista2.MostrarLista()+"\n"+
																"====================================\n"+
																lista3.MostrarLista()+"\n"+
																"====================================\n"+
																lista4.MostrarLista()+"\n"+
																"====================================\n"+
																lista5.MostrarLista()+"\n"+
																"====================================\n"+
																lista6.MostrarLista()+"\n"+
																"====================================\n"+
																lista7.MostrarLista()+"\n"+
																"====================================\n"+
																"\n\nInstrucoes\n\n"+
																"1 ou enter para ver nova carta do Monte.\n"+
																"2 para armazenar uma carta do monte em uma pilha.\n"+
																"3 para armazenar uma carta do monte em uma ListaLigada.\n"+
																"4 para armazenar uma carta de uma ListaLigada em uma pilha.\n"+
																"5 para mover as cartas entre as ListasLigadas.\n"+
																"0 para sair do jogo."
															));
			}
			catch(NumberFormatException nfe){
				op = 1;
			}
		return op;
	}

	public static void main(String args[]){
		//Inicializando variaveis, vetores, pilhas, filas e Listas Ligadas
		int op=0, op2=0, op3=0, op4=0, op41=0, op42=0, op43=0, op44=0, op5=0, op51=0, op52=0, op53=0, op54=0, op55=0, op56=0, op57=0;
		Carta[] baralho = new Carta[52];
		Fila monte = new Fila();
		Pilha pilha1 = new Pilha(13);
		Pilha pilha2 = new Pilha(13);
		Pilha pilha3 = new Pilha(13);
		Pilha pilha4 = new Pilha(13);
		ListaLigada lista1 = new ListaLigada();
		ListaLigada lista2 = new ListaLigada();
		ListaLigada lista3 = new ListaLigada();
		ListaLigada lista4 = new ListaLigada();
		ListaLigada lista5 = new ListaLigada();
		ListaLigada lista6 = new ListaLigada();
		ListaLigada lista7 = new ListaLigada();
		//Inicializando Vetor baralho do tipo carta;
		for(int i =0; i <= baralho.length - 1; i = i + 1){
			baralho[i] = new Carta(0,' ',true,' ');
		}
		//Armazenando e embaralhando as cartas do vetor baralho
		baralho = Armazenamento(baralho);
		baralho = EmbaralharRec(baralho,0);
		//Preenchendo a fila
		for(int i = 0; i <= baralho.length -1; i = i + 1){
			monte.Enfileirar(baralho[i]);
		}
		//Preenchendo ListasLigadas
		lista1 = MontandoLista(monte, 1);
		lista2 = MontandoLista(monte, 2);
		lista3 = MontandoLista(monte, 3);
		lista4 = MontandoLista(monte, 4);
		lista5 = MontandoLista(monte, 5);
		lista6 = MontandoLista(monte, 6);
		lista7 = MontandoLista(monte, 7);
		//Apresentação
		JOptionPane.showMessageDialog(null,"Projeto Paciencia\n\n\n"+
										   "Feito por: Guilherme Pereira Gomides         4CCO\n"+
										   "Professora: Lucy Mari       Estrutura de Dados2\n"+
										   "Versão BETA(TESTES)"

									);
		//menu
		do{
			op = Interface(lista1,lista2,lista3,lista4,lista5,lista6,lista7,pilha1,pilha2,pilha3,pilha4,monte);
			switch(op){
				case 0: System.exit(0);
				//Proximo Carta da fila
				case 1: monte.Enfileirar(monte.Desenfileirar()); break;
				//Armazenar uma carta do monte na fila
				case 2: try{
						op2 = Integer.parseInt(JOptionPane.showInputDialog(null,"Em qual das pilhas voce ira armazenar a carta?(1, 2, 3 ou 4)"));
						switch(op2){
							case 1: pilha1 = MonteparaPilha(monte, pilha1); break;
							case 2: pilha2 = MonteparaPilha(monte, pilha2); break;
							case 3: pilha3 = MonteparaPilha(monte, pilha3); break;
							case 4: pilha4 = MonteparaPilha(monte, pilha4); break;
							default: JOptionPane.showMessageDialog(null,"Opcao invalida");
						}
						}
						catch(NumberFormatException nfe){
							JOptionPane.showMessageDialog(null,"Opcao invalida");
						}break;
				//Armazenar uma carta do monte em uma lista ligada
				case 3: try{
						op3 = Integer.parseInt(JOptionPane.showInputDialog(null,"Em qual das listas voce ira inserir a carta?(1, 2, 3, 4, 5, 6 ou 7)"));
						switch(op3){
							case 1: lista1 = MonteparaListaLigada(monte, lista1); break;
							case 2: lista2 = MonteparaListaLigada(monte, lista2); break;
							case 3: lista3 = MonteparaListaLigada(monte, lista3); break;
							case 4: lista4 = MonteparaListaLigada(monte, lista4); break;
							case 5: lista5 = MonteparaListaLigada(monte, lista5); break;
							case 6: lista6 = MonteparaListaLigada(monte, lista6); break;
							case 7: lista7 = MonteparaListaLigada(monte, lista7); break;
							default: JOptionPane.showMessageDialog(null,"Opcao invalida");
						}
						}
						catch(NumberFormatException nfe){
							JOptionPane.showMessageDialog(null,"Opcao invalida");
						}break;
				//Armazenar uma carta de uma lista ligada em uma pilha
				case 4: op4 = Integer.parseInt(JOptionPane.showInputDialog(null,"Em que pilha você deseja armazenar a Carta?(1, 2, 3 ou 4)"));
						switch(op4){
							case 1: try{
								op41 = Integer.parseInt(JOptionPane.showInputDialog(null,"De qual lista saira esta carta?(1, 2, 3, 4, 5, 6, ou 7)"));
								switch(op41){	
									case 1: pilha1 = ListaparaPilha(lista1, pilha1);break;
									case 2: pilha1 = ListaparaPilha(lista2, pilha1);break;
									case 3: pilha1 = ListaparaPilha(lista3, pilha1);break;
									case 4: pilha1 = ListaparaPilha(lista4, pilha1);break;
									case 5: pilha1 = ListaparaPilha(lista5, pilha1);break;
									case 6: pilha1 = ListaparaPilha(lista6, pilha1);break;
									case 7: pilha1 = ListaparaPilha(lista7, pilha1);break;
									default: JOptionPane.showMessageDialog(null,"Opcao Invalida");
								}
								}
								catch(NumberFormatException nfe){
									JOptionPane.showMessageDialog(null,"Opcao invalida");
								}break;
							case 2: try{
								op42 = Integer.parseInt(JOptionPane.showInputDialog(null,"De qual lista saira esta carta?(1, 2, 3, 4, 5, 6, ou 7)"));
								switch(op42){	
									case 1: pilha2 = ListaparaPilha(lista1, pilha2);break;
									case 2: pilha2 = ListaparaPilha(lista2, pilha2);break;
									case 3: pilha2 = ListaparaPilha(lista3, pilha2);break;
									case 4: pilha2 = ListaparaPilha(lista4, pilha2);break;
									case 5: pilha2 = ListaparaPilha(lista5, pilha2);break;
									case 6: pilha2 = ListaparaPilha(lista6, pilha2);break;
									case 7: pilha2 = ListaparaPilha(lista7, pilha2);break;
									default: JOptionPane.showMessageDialog(null,"Opcao Invalida");
								}
								}
								catch(NumberFormatException nfe){
									JOptionPane.showMessageDialog(null,"Opcao invalida");
								}break;
							case 3: try{
								op43 = Integer.parseInt(JOptionPane.showInputDialog(null,"De qual lista saira esta carta?(1, 2, 3, 4, 5, 6, ou 7)"));
								switch(op43){	
									case 1: pilha3 = ListaparaPilha(lista1, pilha3);break;
									case 2: pilha3 = ListaparaPilha(lista2, pilha3);break;
									case 3: pilha3 = ListaparaPilha(lista3, pilha3);break;
									case 4: pilha3 = ListaparaPilha(lista4, pilha3);break;
									case 5: pilha3 = ListaparaPilha(lista5, pilha3);break;
									case 6: pilha3 = ListaparaPilha(lista6, pilha3);break;
									case 7: pilha3 = ListaparaPilha(lista7, pilha3);break;
									default: JOptionPane.showMessageDialog(null,"Opcao Invalida");
								}
								}
								catch(NumberFormatException nfe){
									JOptionPane.showMessageDialog(null,"Opcao invalida");
								}break;
							case 4: try{
								op44 = Integer.parseInt(JOptionPane.showInputDialog(null,"De qual lista saira esta carta?(1, 2, 3, 4, 5, 6, ou 7)"));
								switch(op44){	
									case 1: pilha4 = ListaparaPilha(lista1, pilha4);break;
									case 2: pilha4 = ListaparaPilha(lista2, pilha4);break;
									case 3: pilha4 = ListaparaPilha(lista3, pilha4);break;
									case 4: pilha4 = ListaparaPilha(lista4, pilha4);break;
									case 5: pilha4 = ListaparaPilha(lista5, pilha4);break;
									case 6: pilha4 = ListaparaPilha(lista6, pilha4);break;
									case 7: pilha4 = ListaparaPilha(lista7, pilha4);break;
									default: JOptionPane.showMessageDialog(null,"Opcao Invalida");
								}
								}
								catch(NumberFormatException nfe){
									JOptionPane.showMessageDialog(null,"Opcao invalida");
								}break;													
						}break;
				//Movimentar as cartas dentro das listas ligadas
				case 5: try{
							op5 = Integer.parseInt(JOptionPane.showInputDialog(null,"De qual lista voce ira mover a(s) carta(s)?(1, 2, 3, 4, 5, 6 ou 7)"));
							switch(op5){
								case 1: try{
											op51 = Integer.parseInt(JOptionPane.showInputDialog(null,"Para qual lista voce ira mover a(s) carta(s)?(2, 3, 4, 5, 6 ou 7?)"));
											switch(op51){
												case 2: lista2 = ListaparaLista(lista1, lista2);break;
												case 3: lista3 = ListaparaLista(lista1, lista3);break;
												case 4: lista4 = ListaparaLista(lista1, lista4);break;
												case 5: lista5 = ListaparaLista(lista1, lista5);break;
												case 6: lista6 = ListaparaLista(lista1, lista6);break;
												case 7: lista7 = ListaparaLista(lista1, lista7);break;
												default: JOptionPane.showMessageDialog(null,"Opcao invalida");
											}
										}
										catch(NumberFormatException nfe){
											JOptionPane.showMessageDialog(null,"Opcao invalida");
										}break;
								case 2: try{
											op52 = Integer.parseInt(JOptionPane.showInputDialog(null,"Para qual lista voce ira mover a(s) carta(s)?(1, 3, 4, 5, 6 ou 7?)"));
											switch(op52){
												case 1: lista1 = ListaparaLista(lista2, lista1);break;
												case 3: lista3 = ListaparaLista(lista2, lista3);break;
												case 4: lista4 = ListaparaLista(lista2, lista4);break;
												case 5: lista5 = ListaparaLista(lista2, lista5);break;
												case 6: lista6 = ListaparaLista(lista2, lista6);break;
												case 7: lista7 = ListaparaLista(lista2, lista7);break;
												default: JOptionPane.showMessageDialog(null,"Opcao invalida");
											}
										}
										catch(NumberFormatException nfe){
											JOptionPane.showMessageDialog(null,"Opcao invalida");
										}break;
								case 3: try{
											op53 = Integer.parseInt(JOptionPane.showInputDialog(null,"Para qual lista voce ira mover a(s) carta(s)?(1, 2, 4, 5, 6 ou 7?)"));
											switch(op53){
												case 1: lista1 = ListaparaLista(lista3, lista1);break;
												case 2: lista2 = ListaparaLista(lista3, lista2);break;
												case 4: lista4 = ListaparaLista(lista3, lista4);break;
												case 5: lista5 = ListaparaLista(lista3, lista5);break;
												case 6: lista6 = ListaparaLista(lista3, lista6);break;
												case 7: lista7 = ListaparaLista(lista3, lista7);break;
												default: JOptionPane.showMessageDialog(null,"Opcao invalida");
											}
										}
										catch(NumberFormatException nfe){
											JOptionPane.showMessageDialog(null,"Opcao invalida");
										}break;
								case 4: try{
											op54 = Integer.parseInt(JOptionPane.showInputDialog(null,"Para qual lista voce ira mover a(s) carta(s)?(1, 2, 3, 5, 6 ou 7?)"));
											switch(op54){
												case 1: lista1 = ListaparaLista(lista4, lista1);break;
												case 2: lista2 = ListaparaLista(lista4, lista2);break;
												case 3: lista3 = ListaparaLista(lista4, lista3);break;
												case 5: lista5 = ListaparaLista(lista4, lista5);break;
												case 6: lista6 = ListaparaLista(lista4, lista6);break;
												case 7: lista7 = ListaparaLista(lista4, lista7);break;
												default: JOptionPane.showMessageDialog(null,"Opcao invalida");
											}
										}
										catch(NumberFormatException nfe){
											JOptionPane.showMessageDialog(null,"Opcao invalida");
										}break;
								case 5: try{
											op55 = Integer.parseInt(JOptionPane.showInputDialog(null,"Para qual lista voce ira mover a(s) carta(s)?(1, 2, 3, 4, 6 ou 7?)"));
											switch(op55){
												case 1: lista1 = ListaparaLista(lista5, lista1);break;
												case 2: lista2 = ListaparaLista(lista5, lista2);break;
												case 3: lista3 = ListaparaLista(lista5, lista3);break;
												case 4: lista4 = ListaparaLista(lista5, lista4);break;
												case 6: lista6 = ListaparaLista(lista5, lista6);break;
												case 7: lista7 = ListaparaLista(lista5, lista7);break;
												default: JOptionPane.showMessageDialog(null,"Opcao invalida");
											}
										}
										catch(NumberFormatException nfe){
											JOptionPane.showMessageDialog(null,"Opcao invalida");
										}break;	
								case 6: try{
											op56 = Integer.parseInt(JOptionPane.showInputDialog(null,"Para qual lista voce ira mover a(s) carta(s)?(1, 2, 3, 4, 5 ou 7?)"));
											switch(op56){
												case 1: lista1 = ListaparaLista(lista6, lista1);break;
												case 2: lista2 = ListaparaLista(lista6, lista2);break;
												case 3: lista3 = ListaparaLista(lista6, lista3);break;
												case 4: lista4 = ListaparaLista(lista6, lista4);break;
												case 5: lista5 = ListaparaLista(lista6, lista5);break;
												case 7: lista7 = ListaparaLista(lista6, lista7);break;
												default: JOptionPane.showMessageDialog(null,"Opcao invalida");
											}
										}
										catch(NumberFormatException nfe){
											JOptionPane.showMessageDialog(null,"Opcao invalida");
										}break;
								case 7: try{
											op57 = Integer.parseInt(JOptionPane.showInputDialog(null,"Para qual lista voce ira mover a(s) carta(s)?(1, 2, 3, 4, 5 ou 6?)"));
											switch(op57){
												case 1: lista1 = ListaparaLista(lista7, lista1);break;
												case 2: lista2 = ListaparaLista(lista7, lista2);break;
												case 3: lista3 = ListaparaLista(lista7, lista3);break;
												case 4: lista4 = ListaparaLista(lista7, lista4);break;
												case 5: lista5 = ListaparaLista(lista7, lista5);break;
												case 6: lista6 = ListaparaLista(lista7, lista6);break;
												default: JOptionPane.showMessageDialog(null,"Opcao invalida");
											}
										}
										catch(NumberFormatException nfe){
											JOptionPane.showMessageDialog(null,"Opcao invalida");
										}break;										
								default: JOptionPane.showMessageDialog(null,"Opcao invalida");
							}
						}
						catch(NumberFormatException nfe){
							JOptionPane.showMessageDialog(null,"Opcao invalida");
						}break;
			}
			//Jogo finalizado
			if((!pilha1.Tavazio())&&(!pilha2.Tavazio())&&(!pilha3.Tavazio())&&(!pilha4.Tavazio())){
				if((pilha1.CartaTopo().numero == 13)&&(pilha2.CartaTopo().numero == 13)&&(pilha3.CartaTopo().numero == 13)&&(pilha4.CartaTopo().numero == 13)){
					JOptionPane.showMessageDialog(null,"Parabens, jogo finalizado!");
					System.exit(0);
				}
			}
		}while(op!=0);
	}

}