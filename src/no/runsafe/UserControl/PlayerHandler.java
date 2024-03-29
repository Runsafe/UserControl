package no.runsafe.UserControl;

import no.runsafe.UserControl.database.PlayerData;
import no.runsafe.UserControl.database.PlayerDatabase;
import no.runsafe.framework.api.hook.IPlayerSeen;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.tools.TimeFormatter;

public class PlayerHandler implements IPlayerSeen
{
	public PlayerHandler(PlayerDatabase database)
	{
		this.database = database;
	}

	@Override
	public String GetLastSeen(IPlayer player, IPlayer checker)
	{
		return this.lastSeenPlayer(player, checker);
	}

	public String lastSeenPlayer(IPlayer player, IPlayer checker)
	{
		PlayerData data = database.getData(player);
		if (data.getBanned() != null)
			return String.format(
				"Player %s has been &4banned&r since %s",
				player.getPrettyName(),
				TimeFormatter.formatInstant(data.getBanned())
			);

		if (player.isOnline() && (checker == null || !checker.shouldNotSee(player)))
			return String.format(
				"Player %s is &aonline&r since %s",
				player.getPrettyName(),
				TimeFormatter.formatInstant(data.getLogin())
			);

		return String.format(
			"Player %s is &coffline&r since %s",
			player.getPrettyName(),
			TimeFormatter.formatInstant(player.isOnline() ? data.getLogin() : data.getLogout())
		);
	}

	private final PlayerDatabase database;
}
