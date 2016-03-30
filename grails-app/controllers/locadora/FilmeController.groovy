package locadora

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FilmeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Filme.list(params), model:[filmeCount: Filme.count()]
    }

    def show(Filme filme) {
        respond filme
    }

    def create() {
        respond new Filme(params)
    }

    @Transactional
    def save(Filme filme) {
        if (filme == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (filme.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond filme.errors, view:'create'
            return
        }

        filme.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'filme.label', default: 'Filme'), filme.id])
                redirect filme
            }
            '*' { respond filme, [status: CREATED] }
        }
    }

    def edit(Filme filme) {
        respond filme
    }

    @Transactional
    def update(Filme filme) {
        if (filme == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (filme.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond filme.errors, view:'edit'
            return
        }

        filme.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'filme.label', default: 'Filme'), filme.id])
                redirect filme
            }
            '*'{ respond filme, [status: OK] }
        }
    }

    @Transactional
    def delete(Filme filme) {

        if (filme == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        filme.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'filme.label', default: 'Filme'), filme.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'filme.label', default: 'Filme'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
