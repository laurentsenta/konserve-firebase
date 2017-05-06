
figwheel:
	lein figwheel testbook

test:
	lein doo phantom auto

jar:
	lein jar

deploy:
	lein deploy clojars

.PHONY: test

