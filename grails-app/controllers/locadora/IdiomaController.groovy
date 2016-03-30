package locadora

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class IdiomaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Idioma.list(params), model:[idiomaCount: Idioma.count()]
    }

    def show(Idioma idioma) {
        respond idioma
    }

    def create() {
        respond new Idioma(params)
    }

    @Transactional
    def save(Idioma idioma) {
        if (idioma == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (idioma.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond idioma.errors, view:'create'
            return
        }

        idioma.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'idioma.label', default: 'Idioma'), idioma.id])
                redirect idioma
            }
            '*' { respond idioma, [status: CREATED] }
        }
    }

    def edit(Idioma idioma) {
        respond idioma
    }

    @Transactional
    def update(Idioma idioma) {
        if (idioma == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (idioma.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond idioma.errors, view:'edit'
            return
        }

        idioma.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'idioma.label', default: 'Idioma'), idioma.id])
                redirect idioma
            }
            '*'{ respond idioma, [status: OK] }
        }
    }

    @Transactional
    def delete(Idioma idioma) {

        if (idioma == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        idioma.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'idioma.label', default: 'Idioma'), idioma.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'idioma.label', default: 'Idioma'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
