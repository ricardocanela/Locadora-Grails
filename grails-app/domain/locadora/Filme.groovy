package locadora

class Filme {

	String tituloOriginal
	String tituloPortugues
	String sinopse
	int ano
	int duracao

	static hasMany = [atores:Ator, diretores:Diretor, idiomaAudio:Idioma, idiomaLegenda:Idioma, generos:Genero]

	static mapping = {
		sinopse sqlType: 'longText'
	}

	String toString(){
		return tituloPortugues
	}

    static constraints = {
    	atores nullable:true
    	diretores nullable:true
    	idiomaLegenda nullable:true
    	idiomaAudio nullable:true
    }
}
