package locadora

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DiretorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Diretor.list(params), model:[diretorCount: Diretor.count()]
    }

    def show(Diretor diretor) {
        respond diretor
    }

    def create() {
        respond new Diretor(params)
    }

    @Transactional
    def save(Diretor diretor) {
        if (diretor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (diretor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond diretor.errors, view:'create'
            return
        }

        diretor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'diretor.label', default: 'Diretor'), diretor.id])
                redirect diretor
            }
            '*' { respond diretor, [status: CREATED] }
        }
    }

    def edit(Diretor diretor) {
        respond diretor
    }

    @Transactional
    def update(Diretor diretor) {
        if (diretor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (diretor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond diretor.errors, view:'edit'
            return
        }

        diretor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'diretor.label', default: 'Diretor'), diretor.id])
                redirect diretor
            }
            '*'{ respond diretor, [status: OK] }
        }
    }

    @Transactional
    def delete(Diretor diretor) {

        if (diretor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        diretor.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'diretor.label', default: 'Diretor'), diretor.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'diretor.label', default: 'Diretor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
