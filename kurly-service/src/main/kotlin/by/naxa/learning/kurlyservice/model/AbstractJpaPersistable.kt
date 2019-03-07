package by.naxa.learning.kurlyservice.model

import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractJpaPersistable<T : Serializable> {

    companion object {
        private val serialVersionUID = -5554308939380869754L
    }

    @Id
    @GeneratedValue
    var id: T? = null

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"

}
