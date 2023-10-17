package by.naxa.learning.kurlyservice.repository

import by.naxa.learning.kurlyservice.model.Link
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import jakarta.transaction.Transactional
import jakarta.transaction.Transactional.TxType.MANDATORY

@Transactional(MANDATORY)
@Repository
interface LinkRepository : JpaRepository<Link, Long> {
    fun findByShortCode(shortCode: String): Link?
}
