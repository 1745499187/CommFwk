package com.chinawiserv.fwk.comm.tcp.mina.protocol.alertserver;

public enum EASDecodeState {
	NEW,
	HEAD,
	MSG,
	MSG_IMCOMPLETE;
}
