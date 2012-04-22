package grailsrest

import org.springframework.dao.DataIntegrityViolationException

class FrontController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [frontInstanceList: Front.list(params), frontInstanceTotal: Front.count()]
    }

    def create() {
        [frontInstance: new Front(params)]
    }

    def save() {
        def frontInstance = new Front(params)
        if (!frontInstance.save(flush: true)) {
            render(view: "create", model: [frontInstance: frontInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'front.label', default: 'Front'), frontInstance.id])
        redirect(action: "show", id: frontInstance.id)
    }

    def show() {
        def frontInstance = Front.get(params.id)
        if (!frontInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'front.label', default: 'Front'), params.id])
            redirect(action: "list")
            return
        }

        [frontInstance: frontInstance]
    }

    def edit() {
        def frontInstance = Front.get(params.id)
        if (!frontInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'front.label', default: 'Front'), params.id])
            redirect(action: "list")
            return
        }

        [frontInstance: frontInstance]
    }

    def update() {
        def frontInstance = Front.get(params.id)
        if (!frontInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'front.label', default: 'Front'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (frontInstance.version > version) {
                frontInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'front.label', default: 'Front')] as Object[],
                          "Another user has updated this Front while you were editing")
                render(view: "edit", model: [frontInstance: frontInstance])
                return
            }
        }

        frontInstance.properties = params

        if (!frontInstance.save(flush: true)) {
            render(view: "edit", model: [frontInstance: frontInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'front.label', default: 'Front'), frontInstance.id])
        redirect(action: "show", id: frontInstance.id)
    }

    def delete() {
        def frontInstance = Front.get(params.id)
        if (!frontInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'front.label', default: 'Front'), params.id])
            redirect(action: "list")
            return
        }

        try {
            frontInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'front.label', default: 'Front'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'front.label', default: 'Front'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
