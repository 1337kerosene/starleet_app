package com.app.starleet.roomdb

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _lactateDao: Lazy<LactateDao> = lazy {
    LactateDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "0dfa90ffe5de39e38343ba2b454a61ff", "98c944eacb5dce4408ef0aca617c68d2") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `lactate_scans` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `lactateValue` REAL NOT NULL, `sweatStatus` TEXT NOT NULL, `changeFromLast` REAL NOT NULL, `timestamp` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '0dfa90ffe5de39e38343ba2b454a61ff')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `lactate_scans`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsLactateScans: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLactateScans.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLactateScans.put("lactateValue", TableInfo.Column("lactateValue", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLactateScans.put("sweatStatus", TableInfo.Column("sweatStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLactateScans.put("changeFromLast", TableInfo.Column("changeFromLast", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLactateScans.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLactateScans: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesLactateScans: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoLactateScans: TableInfo = TableInfo("lactate_scans", _columnsLactateScans, _foreignKeysLactateScans, _indicesLactateScans)
        val _existingLactateScans: TableInfo = read(connection, "lactate_scans")
        if (!_infoLactateScans.equals(_existingLactateScans)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |lactate_scans(com.app.starleet.roomdb.LactateScanEntity).
              | Expected:
              |""".trimMargin() + _infoLactateScans + """
              |
              | Found:
              |""".trimMargin() + _existingLactateScans)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "lactate_scans")
  }

  public override fun clearAllTables() {
    super.performClear(false, "lactate_scans")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(LactateDao::class, LactateDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun lactateDao(): LactateDao = _lactateDao.value
}
