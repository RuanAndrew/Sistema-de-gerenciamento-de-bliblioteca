package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.entities.Aluno;
import br.edu.ifpe.lpoo.project.entities.Professor;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.data.AlunoRepository;
import br.edu.ifpe.lpoo.project.exceptions.DbException;
import br.edu.ifpe.lpoo.project.data.ProfessorRepository;
import br.edu.ifpe.lpoo.project.entities.Pesquisador;
import br.edu.ifpe.lpoo.project.data.PesquisadorRepository;

import java.time.LocalDate;

public class MembroService {

    public void cadastrarAluno(String nome, String cpf, String email, String telefone,
                                String matricula, String tipomembro, String debitomultas,
                                String statusmembro, String curso) {

        if (nome == null || nome.isBlank()) {
            throw new BusinessExcepition("O campo nome não pode ser vazio");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new BusinessExcepition("O campo CPF não pode ser vazio");
        }
        if (!cpf.matches("\\d{11}")) {
		    throw new BusinessExcepition("O CPF deve conter exatamente 11 números.");
		}
        if (email == null || email.isBlank()) {
            throw new BusinessExcepition("O campo email não pode ser vazio");
        }
        if (telefone == null || telefone.isBlank()) {
            throw new BusinessExcepition("O campo telefone não pode ser vazio");
        }
        if (!telefone.matches("\\d{10,11}")) {
		    throw new BusinessExcepition("O telefone deve conter apenas números (10 ou 11 dígitos).");
		}
        if (matricula == null || matricula.isBlank()) {
            throw new BusinessExcepition("O campo matrícula não pode ser vazio");
        }
        if (tipomembro == null || tipomembro.isBlank()) {
            throw new BusinessExcepition("O campo tipo de membro não pode ser vazio");
        }
        if (!tipomembro.equalsIgnoreCase("Aluno") &&
        	    !tipomembro.equalsIgnoreCase("Professor") &&
        	    !tipomembro.equalsIgnoreCase("Pesquisador")) {
        	    throw new BusinessExcepition("Tipo de membro inválido.");
        	}
        if (debitomultas == null || debitomultas.isBlank()) {
            throw new BusinessExcepition("O campo débito de multas não pode ser vazio");
        }
        if (statusmembro == null || statusmembro.isBlank()) {
            throw new BusinessExcepition("O campo status do membro não pode ser vazio");
        }
        if (!statusmembro.equalsIgnoreCase("Ativo") &&
			    !statusmembro.equalsIgnoreCase("Inativo") &&
			    !statusmembro.equalsIgnoreCase("Suspenso") &&
			    !statusmembro.equalsIgnoreCase("Bloqueado") &&
			    !statusmembro.equalsIgnoreCase("ativo") &&
			    !statusmembro.equalsIgnoreCase("inativo") &&
			    !statusmembro.equalsIgnoreCase("suspenso") &&
			    !statusmembro.equalsIgnoreCase("bloqueado")) {
			    throw new BusinessExcepition("Tipo de statusmembro é inválido.");
        if (curso == null || curso.isBlank()) {
            throw new BusinessExcepition("O campo curso não pode ser vazio");
        }

        LocalDate dataCadastro = LocalDate.now();
        Aluno aluno = new Aluno(cpf, nome, telefone, email, dataCadastro, matricula, tipomembro, debitomultas, statusmembro, curso);

        IAlunoRepository alunoRepository = new AlunoRepository();

        boolean exist = alunoRepository.existItem(aluno);

        if (!exist) {
            try {
                alunoRepository.insert(aluno);
            } catch (DbException e) {
                throw new BusinessExcepition("Erro ao cadastrar aluno: " + e.getMessage());
            }
        } else {
            throw new BusinessExcepition("Esse aluno já está cadastrado no sistema.");
        }
        try {
            alunoRepository.delete(cpf); 
        } catch (DbException e) {
            throw new BusinessExcepition("Erro ao excluir aluno: " + e.getMessage());
        }
        public void excluirAluno(String cpf) {
            if (cpf == null || cpf.isBlank()) {
                throw new BusinessExcepition("O campo CPF não pode ser vazio para exclusão.");
            }

            try {
                alunoRepository.delete(cpf);
            } catch (DbException e) {
                throw new BusinessExcepition("Erro ao excluir aluno: " + e.getMessage());
            }
            public void atualizarAluno(Aluno alunoAtualizado) {
                if (alunoAtualizado == null || alunoAtualizado.getCpf() == null || alunoAtualizado.getCpf().isBlank()) {
                    throw new BusinessExcepition("CPF obrigatório para atualizar o aluno.");
                }

                IAlunoRepository alunoRepository = new AlunoRepository();

                boolean existe = alunoRepository.existItem(alunoAtualizado);

                if (existe) {
                    try {
                        alunoRepository.update(alunoAtualizado);
                    } catch (DbException e) {
                        throw new BusinessExcepition("Erro ao atualizar aluno: " + e.getMessage());
                    }
                } else {
                    throw new BusinessExcepition("Aluno não encontrado para atualização.");
                }
            }

    }
    
    public void cadastrarProfessor(String nome, String cpf, String email, String telefone,
                                String matricula, String tipomembro, String debitomultas,
                                String statusmembro) {
				    	 if (nome == null || nome.isBlank()) {
				             throw new BusinessExcepition("O campo nome não pode ser vazio");
				         }
				         if (cpf == null || cpf.isBlank()) {
				             throw new BusinessExcepition("O campo CPF não pode ser vazio");
				         }
				         if (!cpf.matches("\\d{11}")) {
							    throw new BusinessExcepition("O CPF deve conter exatamente 11 números.");
				         }
				         if (email == null || email.isBlank()) {
				             throw new BusinessExcepition("O campo email não pode ser vazio");
				         }
				         if (telefone == null || telefone.isBlank()) {
				             throw new BusinessExcepition("O campo telefone não pode ser vazio");
				         }
				         if (!telefone.matches("\\d{10,11}")) {
							    throw new BusinessExcepition("O telefone deve conter apenas números (10 ou 11 dígitos).");
				         }
				         if (matricula == null || matricula.isBlank()) {
				             throw new BusinessExcepition("O campo matrícula não pode ser vazio");
				         }
				         if (tipomembro == null || tipomembro.isBlank()) {
				             throw new BusinessExcepition("O campo tipo de membro não pode ser vazio");
				         }
				         if (!tipomembro.equalsIgnoreCase("Aluno") &&
				        		    !tipomembro.equalsIgnoreCase("Professor") &&
				        		    !tipomembro.equalsIgnoreCase("Pesquisador")) {
				        		    throw new BusinessExcepition("Tipo de membro inválido.");
				        		}
				         if (debitomultas == null || debitomultas.isBlank()) {
				             throw new BusinessExcepition("O campo débito de multas não pode ser vazio");
				         }
				         if (statusmembro == null || statusmembro.isBlank()) {
				             throw new BusinessExcepition("O campo status do membro não pode ser vazio");
				         }
				         if (!statusmembro.equalsIgnoreCase("Ativo") &&
								    !statusmembro.equalsIgnoreCase("Inativo") &&
								    !statusmembro.equalsIgnoreCase("Suspenso") &&
								    !statusmembro.equalsIgnoreCase("Bloqueado") &&
								    !statusmembro.equalsIgnoreCase("ativo") &&
								    !statusmembro.equalsIgnoreCase("inativo") &&
								    !statusmembro.equalsIgnoreCase("suspenso") &&
								    !statusmembro.equalsIgnoreCase("bloqueado")) {
								    throw new BusinessExcepition("Tipo de statusmembro é inválido.");
				         
				         LocalDate dataCadastro = LocalDate.now();
				         Professor professor = new Professor(cpf, nome, telefone, email, matricula, tipomembro, debitomultas);
				
				         IProfessorRepository professorRepository = new ProfessorRepository();
				
				         boolean exist = ProfessorRepository.existItem(professor);
				
				         if (!exist) {
				             try {
				            	 ProfessorRepository.insert(professor);
				             } catch (DbException e) {
				                 throw new BusinessExcepition("Erro ao cadastrar professor: " + e.getMessage());
				             }
				         } else {
				             throw new BusinessExcepition("Esse professor já está cadastrado no sistema.");
				         }
				         public void excluirProfessor(String cpf) {
				             if (cpf == null || cpf.isBlank()) {
				                 throw new BusinessExcepition("O campo CPF não pode ser vazio para exclusão.");
				             }

				             try {
				                 ProfessorRepository.delete(cpf);
				             } catch (DbException e) {
				                 throw new BusinessExcepition("Erro ao excluir Professor: " + e.getMessage());
				             }
				             public void atualizarAluno(Aluno alunoAtualizado) {
				            	    if (alunoAtualizado == null || alunoAtualizado.getCpf() == null || alunoAtualizado.getCpf().isBlank()) {
				            	        throw new BusinessExcepition("CPF obrigatório para atualizar o aluno.");
				            	    }

				            	    IProfessorRepository professorRepository = new ProfessorRepository();

				            	    boolean existe = professorRepository.existItem(professorAtualizado);

				            	    if (existe) {
				            	        try {
				            	            professorRepository.update(professorAtualizado);
				            	        } catch (DbException e) {
				            	            throw new BusinessExcepition("Erro ao atualizar professor: " + e.getMessage());
				            	        }
				            	    } else {
				            	        throw new BusinessExcepition("Professor não encontrado para atualização.");
				            	    }
				            	}

				     }
    public void CadastrarPesquisador(String nome, String cpf, String email, String telefone,
            String matricula, String tipomembro, String debitomultas,
            String statusmembro) {
			if (nome == null || nome.isBlank()) {
			throw new BusinessExcepition("O campo nome não pode ser vazio");
			}
			if (cpf == null || cpf.isBlank()) {
			throw new BusinessExcepition("O campo CPF não pode ser vazio");
			}
			if (!cpf.matches("\\d{11}")) {
			    throw new BusinessExcepition("O CPF deve conter exatamente 11 números.");
			}
			if (email == null || email.isBlank()) {
			throw new BusinessExcepition("O campo email não pode ser vazio");
			}
			if (telefone == null || telefone.isBlank()) {
			throw new BusinessExcepition("O campo telefone não pode ser vazio");
			}
			if (!telefone.matches("\\d{10,11}")) {
			    throw new BusinessExcepition("O telefone deve conter apenas números (10 ou 11 dígitos).");
			}
			if (matricula == null || matricula.isBlank()) {
			throw new BusinessExcepition("O campo matrícula não pode ser vazio");
			}
			if (tipomembro == null || tipomembro.isBlank()) {
			throw new BusinessExcepition("O campo tipo de membro não pode ser vazio");
			}
			if (!tipomembro.equalsIgnoreCase("Aluno") &&
				    !tipomembro.equalsIgnoreCase("Professor") &&
				    !tipomembro.equalsIgnoreCase("Pesquisador")) {
				    throw new BusinessExcepition("Tipo de membro inválido. Só pode ser: Aluno, Professor ou Pesquisador.");
				}
			if (debitomultas == null || debitomultas.isBlank()) {
			throw new BusinessExcepition("O campo débito de multas não pode ser vazio");
			}
			if (statusmembro == null || statusmembro.isBlank()) {
			throw new BusinessExcepition("O campo status do membro não pode ser vazio");
			}
			if (!statusmembro.equalsIgnoreCase("Ativo") &&
				    !statusmembro.equalsIgnoreCase("Inativo") &&
				    !statusmembro.equalsIgnoreCase("Suspenso") &&
				    !statusmembro.equalsIgnoreCase("Bloqueado") &&
				    !statusmembro.equalsIgnoreCase("ativo") &&
				    !statusmembro.equalsIgnoreCase("inativo") &&
				    !statusmembro.equalsIgnoreCase("suspenso") &&
				    !statusmembro.equalsIgnoreCase("bloqueado")) {
				    throw new BusinessExcepition("Tipo de statusmembro é inválido.");
				}
			
			LocalDate dataCadastro = LocalDate.now();
			Pesquisador pesquisador = new Pesquisador(cpf, nome, telefone, email, matricula, tipomembro, debitomultas);
			
			IPesquisadorRepository pesquisadorRepository = new PesquisadorRepository();
			
			boolean exist = PesquisadorRepository.existItem(professor);
			
			if (!exist) {
			try {
				PesquisadorRepository.insert(pesquisador);
			} catch (DbException e) {
			throw new BusinessExcepition("Erro ao cadastrar Pesquisador: " + e.getMessage());
			}
			} else {
			throw new BusinessExcepition("Esse Pesquisador já está cadastrado no sistema.");
			}
			}
		    public void excluirPesquisador(String cpf) {
		        if (cpf == null || cpf.isBlank()) {
		            throw new BusinessExcepition("O campo CPF não pode ser vazio para exclusão.");
		        }
		
		        try {
		            PesquisadorRepository.delete(cpf);
		        } catch (DbException e) {
		            throw new BusinessExcepition("Erro ao excluir Pesquisador: " + e.getMessage());
		        }
		        public void atualizarAluno(Aluno alunoAtualizado) {
		            if (pesquisadorAtualizado == null || pesquisadorAtualizado.getCpf() == null || pesquisadorAtualizado.getCpf().isBlank()) {
		                throw new BusinessExcepition("CPF obrigatório para atualizar o pesquisador.");
		            }

		            IPesquisadorRepository pesquisadorRepository = new PesquisadorRepository();

		            boolean existe = pesquisadorRepository.existItem(pesquisadorAtualizado);

		            if (existe) {
		                try {
		                    pesquisadoroRepository.update(pesquisadorAtualizado);
		                } catch (DbException e) {
		                    throw new BusinessExcepition("Erro ao atualizar pesquisador: " + e.getMessage());
		                }
		            } else {
		                throw new BusinessExcepition("pesquisador não encontrado para atualização.");
		            }
		        }

    }