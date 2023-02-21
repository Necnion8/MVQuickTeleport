# MVQuickTeleport
ロードコマンドを兼ねたmvtpコマンド (+α)

## 前提
- Spigot 1.13 以上 (またはその派生)
- Multiverse-Core
- Multiverse-Portals (オプション)

## コマンドと権限
- テレポートコマンド - `/mvquickteleport`, `/qmvtp`
> 指定されたワールドをロードしてテレポートさせます
> 
> ※ コマンド権限に加え、`multiverse.core.load` が許可されている必要があります。<br>
> ※ Multiverse-Portalsと連携すると、ポータルも利用できるようになります。
> 
> 引数: `/qmvtp (world)`<br>
> 権限: `mvquickteleport.command.mvquickteleport` (default: OP)<br>

## 設定
```yml
# ワールドが作成された時に、autoload 設定を無効にします
force-autoload-disable: true

# 接続時に前回切断したワールドを再ロードして位置を復元します (アンロードされていた場合のみ)
keep-quit-world: true
```
