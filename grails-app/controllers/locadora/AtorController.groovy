package locadora

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AtorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Ator.list(params), model:[atorCount: Ator.count()]
    }

    def show(Ator ator) {
        respond ator
    }

    def create() {
        respond new Ator(params)
    }

    @Transactional
    def save(Ator ator) {
        if (ator == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ator.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ator.errors, view:'create'
            return
        }

        ator.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ator.label', default: 'Ator'), ator.id])
                redirect ator
            }
            '*' { respond ator, [status: CREATED] }
        }
    }

    def edit(Ator ator) {
        respond ator
    }

    @Transactional
    def update(Ator ator) {
        if (ator == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ator.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ator.errors, view:'edit'
            return
        }

        ator.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ator.label', default: 'Ator'), ator.id])
                redirect ator
            }
            '*'{ respond ator, [status: OK] }
        }
    }

    @Transactional
    def delete(Ator ator) {

        if (ator == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ator.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ator.label', default: 'Ator'), ator.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ator.label', default: 'Ator'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
