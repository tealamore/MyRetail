package com.myRetail.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories

@Configuration
@EnableReactiveCassandraRepositories
class CassandraConfig : AbstractReactiveCassandraConfiguration() {

    @Value("\${cassandra.contactPoints}")
    private lateinit var contactPoints: String

    @Value("\${cassandra.port}")
    private var port: Int = 0

    @Value("\${cassandra.keyspace}")
    private lateinit var keyspace: String

    @Value("\${cassandra.basePackages}") //TODO: Why do we need values and why can't we use @ConfigurationProperties
    private lateinit var basePackages: String

    override fun getKeyspaceName(): String {
        return keyspace
    }

    override fun getContactPoints(): String {
        return contactPoints
    }

    override fun getPort(): Int {
        return port
    }

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS
    }

    override fun getEntityBasePackages(): Array<String> {
        return arrayOf(basePackages)
    }

    override fun getKeyspaceCreations(): List<CreateKeyspaceSpecification> {
        val specification = CreateKeyspaceSpecification.createKeyspace(keyspace)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication()
        return listOf(specification)
    }

    override fun getKeyspaceDrops(): List<DropKeyspaceSpecification> {
        return listOf(DropKeyspaceSpecification.dropKeyspace(keyspace))
    }
}