package locadora

class Diretor {

	String nome

	static belongsTo = Filme
	static hasMany = [filmes:Filme]

	String toString(){
		return nome
	}

    static constraints = {

    }
}
