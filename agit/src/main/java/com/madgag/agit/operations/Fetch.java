package com.madgag.agit.operations;

import static android.R.drawable.stat_sys_download;
import static android.R.drawable.stat_sys_download_done;

import java.io.File;

import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RemoteConfig;

import com.google.inject.Inject;
import com.madgag.agit.GitFetchService;
import com.madgag.agit.Progress;
import com.madgag.agit.ProgressListener;

public class Fetch implements GitOperation {
		
	public static final String TAG = "Fetch";
	
	private final File gitdir;
	private final RemoteConfig remote;

	@Inject GitFetchService fetchService;
	
	public Fetch(File gitdir, RemoteConfig remote) {
		this.gitdir = gitdir;
		this.remote = remote;
    }
	
	public int getOngoingIcon() {
		return stat_sys_download;
	}

	public String getTickerText() {
		return "Fetching "+remote.getName() + " " + fetchUrl();
	}
	
	public OpNotification execute(ProgressListener<Progress> progressListener) {
		FetchResult r = fetchService.fetch(remote, progressListener);
		return new OpNotification(stat_sys_download_done,"Fetch complete", "Fetched "+remote.getName(), fetchUrl());
    }
	
	public String getName() {
		return "Fetch";
	}
	
	public String getDescription() {
		return "fetching "+remote.getName() + " " + fetchUrl();
	}

	private String fetchUrl() {
		return remote.getURIs().get(0).toString();
	}

	public CharSequence getUrl() {
		return fetchUrl();
	}

	public String getShortDescription() {
		return "Fetching "+remote.getName();
	}

	public File getGitDir() {
		return gitdir;
	}
}
