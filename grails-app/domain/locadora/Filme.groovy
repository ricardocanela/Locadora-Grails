package locadora

class Filme {

	String tituloOriginal
	String tituloPortugues
	String sinopse
	int ano
	int duracao

	static hasMany = [atores:Ator]

	static mapping = {
		sinopse sqlType: 'longText'
	}

    static constraints = {
    	atores nullable:true
    }
}
