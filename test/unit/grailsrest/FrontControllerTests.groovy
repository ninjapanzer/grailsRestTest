package grailsrest



import org.junit.*
import grails.test.mixin.*

@TestFor(FrontController)
@Mock(Front)
class FrontControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/front/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.frontInstanceList.size() == 0
        assert model.frontInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.frontInstance != null
    }

    void testSave() {
        controller.save()

        assert model.frontInstance != null
        assert view == '/front/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/front/show/1'
        assert controller.flash.message != null
        assert Front.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/front/list'


        populateValidParams(params)
        def front = new Front(params)

        assert front.save() != null

        params.id = front.id

        def model = controller.show()

        assert model.frontInstance == front
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/front/list'


        populateValidParams(params)
        def front = new Front(params)

        assert front.save() != null

        params.id = front.id

        def model = controller.edit()

        assert model.frontInstance == front
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/front/list'

        response.reset()


        populateValidParams(params)
        def front = new Front(params)

        assert front.save() != null

        // test invalid parameters in update
        params.id = front.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/front/edit"
        assert model.frontInstance != null

        front.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/front/show/$front.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        front.clearErrors()

        populateValidParams(params)
        params.id = front.id
        params.version = -1
        controller.update()

        assert view == "/front/edit"
        assert model.frontInstance != null
        assert model.frontInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/front/list'

        response.reset()

        populateValidParams(params)
        def front = new Front(params)

        assert front.save() != null
        assert Front.count() == 1

        params.id = front.id

        controller.delete()

        assert Front.count() == 0
        assert Front.get(front.id) == null
        assert response.redirectedUrl == '/front/list'
    }
}
