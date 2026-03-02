package com.app.starleet.roomdb

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class LactateDao_Impl(
  __db: RoomDatabase,
) : LactateDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfLactateScanEntity: EntityInsertAdapter<LactateScanEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfLactateScanEntity = object : EntityInsertAdapter<LactateScanEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `lactate_scans` (`id`,`lactateValue`,`sweatStatus`,`changeFromLast`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LactateScanEntity) {
        statement.bindLong(1, entity.id)
        statement.bindDouble(2, entity.lactateValue)
        statement.bindText(3, entity.sweatStatus)
        statement.bindDouble(4, entity.changeFromLast)
        statement.bindLong(5, entity.timestamp)
      }
    }
  }

  public override suspend fun insertScan(scan: LactateScanEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfLactateScanEntity.insert(_connection, scan)
  }

  public override fun getAllScans(): Flow<List<LactateScanEntity>> {
    val _sql: String = "SELECT * FROM lactate_scans ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("lactate_scans")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfLactateValue: Int = getColumnIndexOrThrow(_stmt, "lactateValue")
        val _columnIndexOfSweatStatus: Int = getColumnIndexOrThrow(_stmt, "sweatStatus")
        val _columnIndexOfChangeFromLast: Int = getColumnIndexOrThrow(_stmt, "changeFromLast")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<LactateScanEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LactateScanEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpLactateValue: Double
          _tmpLactateValue = _stmt.getDouble(_columnIndexOfLactateValue)
          val _tmpSweatStatus: String
          _tmpSweatStatus = _stmt.getText(_columnIndexOfSweatStatus)
          val _tmpChangeFromLast: Double
          _tmpChangeFromLast = _stmt.getDouble(_columnIndexOfChangeFromLast)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item = LactateScanEntity(_tmpId,_tmpLactateValue,_tmpSweatStatus,_tmpChangeFromLast,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getLast7Scans(): Flow<List<LactateScanEntity>> {
    val _sql: String = "SELECT * FROM lactate_scans ORDER BY timestamp DESC LIMIT 7"
    return createFlow(__db, false, arrayOf("lactate_scans")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfLactateValue: Int = getColumnIndexOrThrow(_stmt, "lactateValue")
        val _columnIndexOfSweatStatus: Int = getColumnIndexOrThrow(_stmt, "sweatStatus")
        val _columnIndexOfChangeFromLast: Int = getColumnIndexOrThrow(_stmt, "changeFromLast")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<LactateScanEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LactateScanEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpLactateValue: Double
          _tmpLactateValue = _stmt.getDouble(_columnIndexOfLactateValue)
          val _tmpSweatStatus: String
          _tmpSweatStatus = _stmt.getText(_columnIndexOfSweatStatus)
          val _tmpChangeFromLast: Double
          _tmpChangeFromLast = _stmt.getDouble(_columnIndexOfChangeFromLast)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item = LactateScanEntity(_tmpId,_tmpLactateValue,_tmpSweatStatus,_tmpChangeFromLast,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLatestScan(): LactateScanEntity? {
    val _sql: String = "SELECT * FROM lactate_scans ORDER BY timestamp DESC LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfLactateValue: Int = getColumnIndexOrThrow(_stmt, "lactateValue")
        val _columnIndexOfSweatStatus: Int = getColumnIndexOrThrow(_stmt, "sweatStatus")
        val _columnIndexOfChangeFromLast: Int = getColumnIndexOrThrow(_stmt, "changeFromLast")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: LactateScanEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpLactateValue: Double
          _tmpLactateValue = _stmt.getDouble(_columnIndexOfLactateValue)
          val _tmpSweatStatus: String
          _tmpSweatStatus = _stmt.getText(_columnIndexOfSweatStatus)
          val _tmpChangeFromLast: Double
          _tmpChangeFromLast = _stmt.getDouble(_columnIndexOfChangeFromLast)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _result = LactateScanEntity(_tmpId,_tmpLactateValue,_tmpSweatStatus,_tmpChangeFromLast,_tmpTimestamp)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
